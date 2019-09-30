package com.bonc.aocemp.utils;

import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 树处理工具
 * @param <K> id类型
 * @param <T>
 *
 * @author yzx
 * @date 2019/9/25
 */
public interface TreeDealTool <K, T extends TreeUtil> {

    /**
     * 条件查询，层层筛选
     * @param children
     * @param keyword
     */
    default void findByKeyword(List<T> children, String keyword) {
        int cnt = getTreeLayers(children, 2);
        for (int i = 0; i < cnt; i++) {
            handleTreeFindByKeyword(children, keyword);
        }
    }

    /**
     * 条件查询
     * @param children 根节点下一层开始
     * @param keyword 查询关键字
     */
    default void handleTreeFindByKeyword(List<T> children, String keyword) {
        Iterator<T> iterator = children.iterator();
        while (iterator.hasNext()) {
            T model = iterator.next();
            if (model.getChildren().size() == 0 && (StringUtils.isEmpty(model.getName())
                    || !model.getName().toLowerCase().contains(keyword.toLowerCase()))) {
                iterator.remove();
            } else if (model.getChildren().size() > 0) {
                handleTreeFindByKeyword(model.getChildren(), keyword);
            }
        }
    }

    /**
     * 往父节点上挂子节点
     * @param parentMap K父级id
     * @param childMap
     */
    default void addChildToParent(Map<K, List<T>> parentMap, Map<K, List<T>> childMap) {
        parentMap.values().stream().flatMap(Collection::stream).forEach(obj -> obj.setChildren(childMap.get(obj.getId())));
    }

    /**
     * 往父节点上挂子节点
     * @param parentList 父级list
     * @param childMap
     */
    default void addChildToParent(List<T> parentList, Map<K, List<T>> childMap) {
        parentList.stream().forEach(obj -> obj.setChildren(childMap.get(obj.getId())));
    }

    /**
     * 构建树
     * @param modelList 自定义根节点下面一层
     * @param deptTreeModelMap 父节点id和对应T集合
     */
    default void handleDeptTree(List<T> modelList, Map<Integer, List<T>> deptTreeModelMap) {
        modelList.stream().filter(Objects::nonNull).peek(item -> item.setChildren(deptTreeModelMap.get(item.getId())))
                .forEach(item -> {
                    if (item.getChildren().size() > 0) {
                        handleDeptTree(item.getChildren(), deptTreeModelMap);
                    }
                });
    }

    /**
     * 排序
     * @param treeModel
     */
    default void sortTreeModel(T treeModel) {
        List<T> children = treeModel.getChildren();
        if (children == null || children.size() == 0) {
            return;
        }
        children.sort(Comparator.comparing(T::getSortNo));
        children.forEach(this::sortTreeModel);
    }

    /**
     * 获取树层级数
     * @param trees
     * @param layer
     * @return
     */
    default int getTreeLayers(List<T> trees, int layer) {
        int curNum = layer;
        for (T tree : trees) {
            if (tree.getChildren() != null && tree.getChildren().size() > 0) {
                int num = getTreeLayers(tree.getChildren(), layer + 1);
                if (num > curNum ) {
                    curNum = num;
                }
            } else {
                if (layer + 1 > curNum) {
                    curNum = layer + 1;
                }
            }
        }
        return curNum;
    }

}
