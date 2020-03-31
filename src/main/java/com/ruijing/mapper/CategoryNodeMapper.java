package com.ruijing.mapper;

import com.ruijing.model.CategoryNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Kimmy
 */
@Mapper
@Repository
public interface CategoryNodeMapper {

    /**
     * 获取所有的节点
     * @return
     */
    List<CategoryNode>  getAll();

    /**
     * 根据ID获得当前的分类节点
     * @param id
     * @return
     */
    CategoryNode findById(int id);

    /**
     * 获取全部的根节点
     * @return
     */
    List<CategoryNode> getRoots();

    /**
     * 根据父节点获得全部的子节点
     * @param id
     * @return
     */
    List<CategoryNode> getSubCategoryNodesIncludingSelf(int id);

    int addCategoryTo(@Param("name") String name, @Param("parentId") int parentId);

    void deleteCategory(int id);

    int getMaxRightValue();

    void addCategories(List<CategoryNode> nodes);
}
