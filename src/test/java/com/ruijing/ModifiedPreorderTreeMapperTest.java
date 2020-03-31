package com.ruijing;

import com.ruijing.mapper.CategoryNodeMapper;
import com.ruijing.model.CategoryNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ModifiedPreorderTreeMapperTest {

    @Autowired
    private CategoryNodeMapper mapper;

    @Test
    public void testGetAll() {
        List<CategoryNode> all = mapper.getAll();
        System.out.println(all);
    }

    @Test
    public void testAdd() {
        mapper.addCategoryTo("testCategory", 2);
    }

    @Test
    public void testDel() {
        mapper.deleteCategory(1);
    }

    @Test
    public void testGetMaxRight() {
        mapper.getMaxRightValue();
    }
}
