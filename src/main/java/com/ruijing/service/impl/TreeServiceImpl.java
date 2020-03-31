package com.ruijing.service.impl;

import com.ruijing.dto.Category;
import com.ruijing.mapper.CategoryNodeMapper;
import com.ruijing.model.CategoryNode;
import com.ruijing.service.TreeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kimmy
 */
@Service
public class TreeServiceImpl implements TreeService {

    @Resource
    private CategoryNodeMapper mapper;

    @Override
    public Category getTree(int rootId) {
        List<CategoryNode> categoryNodes = mapper.getSubCategoryNodesIncludingSelf(rootId);
        if (categoryNodes == null || categoryNodes.size() == 0) {
            return null;
        }
        CategoryNode root = categoryNodes.remove(0);
        return getTree(root, categoryNodes);
    }

    private Category getTree(CategoryNode parentCategory, List<CategoryNode> nodes) {
        Category category = new Category(parentCategory.getId(), parentCategory.getName());
        if (!parentCategory.isLeaf()) {
            while (nodes.size() > 0) {
                CategoryNode tmpNode = nodes.get(0);
                if (tmpNode.getLft() > parentCategory.getRgt()) {
                    break;
                }
                nodes.remove(0);
                category.addSubCategory(getTree(tmpNode, nodes));
            }
        }
        return category;
    }

    @Override
    public List<Category> getRoots() {
        List<CategoryNode> roots = mapper.getRoots();
        List<Category> result = new ArrayList<>();
        for (CategoryNode n : roots) {
            Category root = this.getTree(n.getId());
            result.add(root);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addCategory(String nodeName, int parentId) {
        return mapper.addCategoryTo(nodeName, parentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(int id) {
        mapper.deleteCategory(id);
    }

    /**
     * 这里都不考虑异常情况
     *
     * @param category 节点
     * @return 添加成功数
     */
    @Override
    public int addCategoryList(Category category) {
        int lftValue = mapper.getMaxRightValue() + 1;
        List<CategoryNode> nodes = new ArrayList<>();
        CategoryNode root = labelCategory(category, lftValue, nodes);
        mapper.addCategories(nodes);
        return root.getId();
    }

    /**
     * 传入lftValue并设置各个node的左右值
     *
     * @param category 新节点
     * @param lftValue 左值
     * @return rgtValue 右值
     */
    private CategoryNode labelCategory(Category category, int lftValue, List<CategoryNode> nodes) {
        CategoryNode categoryNode = new CategoryNode();
        nodes.add(categoryNode);
        categoryNode.setName(category.getName());
        categoryNode.setLft(lftValue);
        int rgtValue = lftValue + 1;
        if (category.isLeaf()) {
            categoryNode.setRgt(rgtValue);
        } else {
            for (Category subCategory : category.getSubCategories()) {
                rgtValue = labelCategory(subCategory, rgtValue, nodes).getRgt() + 1;
            }
            categoryNode.setRgt(rgtValue);
        }
        return categoryNode;
    }
}
