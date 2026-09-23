package com.jy.rbac.utils;

import com.jy.rbac.pojo.vo.DeptVO;
import com.jy.rbac.pojo.vo.MenuVO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class TreeUtils {

    private TreeUtils() {
    }

    public static List<DeptVO> buildDeptTree(List<DeptVO> list) {
        return buildTree(list, DeptVO::getDeptId, DeptVO::getParentId, DeptVO::getOrderNum,
                DeptVO::getChildren, DeptVO::setChildren);
    }

    public static List<MenuVO> buildMenuTree(List<MenuVO> list) {
        return buildTree(list, MenuVO::getMenuId, MenuVO::getParentId, MenuVO::getOrderNum,
                MenuVO::getChildren, MenuVO::setChildren);
    }

    /**
     * 通用建树：一次遍历挂父子关系，根/孤儿/自环节点视为根节点，各层按 orderNum 升序排序
     */
    private static <T> List<T> buildTree(List<T> list,
                                         Function<T, Long> idGetter,
                                         Function<T, Long> parentIdGetter,
                                         Function<T, Integer> orderNumGetter,
                                         Function<T, List<T>> childrenGetter,
                                         BiConsumer<T, List<T>> childrenSetter) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Long> ids = list.stream().map(idGetter).collect(Collectors.toSet());
        Map<Long, T> nodeMap = list.stream().collect(Collectors.toMap(idGetter, Function.identity()));
        List<T> roots = new ArrayList<>();
        for (T node : list) {
            Long parentId = parentIdGetter.apply(node);
            if (parentId == null || parentId.equals(idGetter.apply(node)) || !ids.contains(parentId)) {
                roots.add(node);
            } else {
                T parent = nodeMap.get(parentId);
                List<T> children = childrenGetter.apply(parent);
                if (children == null) {
                    children = new ArrayList<>();
                    childrenSetter.accept(parent, children);
                }
                children.add(node);
            }
        }
        sortTree(roots, orderNumGetter, childrenGetter);
        return roots;
    }

    private static <T> void sortTree(List<T> nodes,
                                     Function<T, Integer> orderNumGetter,
                                     Function<T, List<T>> childrenGetter) {
        if (nodes == null || nodes.isEmpty()) {
            return;
        }
        nodes.sort(Comparator.comparing(orderNumGetter, Comparator.nullsLast(Comparator.naturalOrder())));
        for (T node : nodes) {
            sortTree(childrenGetter.apply(node), orderNumGetter, childrenGetter);
        }
    }
}
