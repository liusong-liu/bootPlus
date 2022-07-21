package com.demo.bootplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.bootplus.mapper.UserMapper;
import com.demo.bootplus.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class BootPlusApplicationTests {

    //继承了BaseMapper ,所有方法来自父类，可扩展
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println(("----- selectAll method test 测试查询所有用户方法 ------"));
        //selectList 的参数wrapper 是条件构造器，可以先写null
        List<User> userList = userMapper.selectList(null);
        //forEach 的参数是 Consumer类型的 语法糖
        userList.forEach(System.out::println);
    }

    @Test
    void contextLoadsWithWrapper() {
        //----------查询多个
        //查询一个复杂的，比如查询用户name、邮箱不为空，年龄大于20的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>(); //首先新建一个 QueryWrapper
        //链式编程 添加查询条件
        wrapper.isNotNull("name")
                .eq("email","2455555659@qq.com")
                .ge("age",12);
        userMapper.selectList(wrapper).forEach(System.out::println);
        //----------查询单个
        User user = userMapper.selectOne(wrapper); //出现多个结果会报错，查询一个
        System.out.println("user = " + user);
    }
    //        eq相等   ne不相等，   gt大于，    lt小于         ge大于等于       le 小于等于
    //        equal/ not equal/ greater than/ less than/ less than or equal/ great than or equal/


    //测试插入
    @Test
    public void testInsert(){
        User user = new User();
//        user.setId(6l);
        user.setName("松松");
        user.setAge(31);
        user.setEmail("1476563920@qq.com");
        //没有设置ID却自动生成的ID
        int result = userMapper.insert(user);
        System.out.println("result = " + result);
        System.out.println("user = " + user);
    }

    //更新测试
    @Test
    public void testUpdateByID() {
        User user = new User();
        user.setId(7L);
        user.setName("小小");
        user.setAge(18);//这一行后加
        int i = userMapper.updateById(user);//受影响的行数,参数是一个user不是id,点击看源码
        System.out.println("i = " + i);
    }

    @Test
    public void testOptimisticLocker(){
        //1、查询用户信息
        User user = userMapper.selectById(1L);
        //2、修改用户信息
        user.setEmail("123@qq.com");
        user.setName("小垃圾");
        //3、更新操作
        userMapper.updateById(user);
    }

    @Test
    public void testOptimisticLocker2(){
        //模拟多线程
        User user = userMapper.selectById(3L);
        user.setEmail("123jdw@qq.com");
        user.setName("帅小伙111");//我们在这里对线程1修改值

        //线程2插队
        User user2 = userMapper.selectById(3L);
        user2.setEmail("321jdw@qq.com");
        user2.setName("帅小伙222");
        userMapper.updateById(user2); //线程2抢先提交

        userMapper.updateById(user);//线程1失败，乐观锁在这种情况下防止了脏数据存在，没有乐观锁就会有覆盖掉线程2的操作
    }

    //查询单用户
    @Test
    public void testSelectBatchId(){
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    //查询指定多用户
    @Test
    public void testSelectBatchIds() {
        //Arrays.asList()创建了一个固定大小的集合
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));//参数Collection的集合
        users.forEach(System.out::println);
    }

    //条件查询，-- HashMap
    @Test
    public void testSelectByMap() {
        HashMap<String, Object> map = new HashMap<>();
        //定义查询条件
        map.put("name", "小蒋"); //where k = v
        map.put("age",3);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    //测试分页查询
    @Test
    public void testPage() {
        Page<User> page = new Page<>(1,5); //开启拦截器后，会注册一个page对象  当前页，每页条数
        //方法源码：   <P extends IPage<T>> P selectPage(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
        userMapper.selectPage(page,null); //分页查询
        page.getRecords().forEach(System.out::println); //获取分页后的数据 打印
        System.out.println(page.getTotal()); //获取记录总数
    }

    //删除测试
    @Test
    public void testDeleteById(){
        userMapper.deleteById(1453324799370616833L);
        // userMapper.delete(null); //全部删除
    }

    @Test
    public void testDeleteById1(){
        userMapper.deleteById(1L);
        // userMapper.delete(null); //全部删除
    }

    @Test
    void testQuery() {
        //查询区间内的记录
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age",20,30);
        Integer count = userMapper.selectCount(wrapper);
        System.out.println("count = " + count);
    }

    //模糊查询
    @Test
    void test3() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name",99)         //  名字中 存在 99
                .notLike("name",6)      //  名字中 不存在 6
                .likeRight("email",2)   //  邮箱 最右边是m  %m
                .likeLeft("email","m"); //  邮箱 最左边是2  2%
        userMapper.selectMaps(wrapper);
    }

    //子查询

    @Test
    void test4() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.inSql("id","select id from t_class where id <2");
        userMapper.selectObjs(wrapper).forEach(System.out::println);
    }

    @Test
    void test5() {
        //排序
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");  //根据id升序排列   降序orderByDesc()略
        userMapper.selectList(wrapper).forEach(System.out::println);
    }

    @Test
    void test7() {
        //分组排序
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.groupBy("version").having("version = 1");
        userMapper.selectList(wrapper).forEach(System.out::println);
    }








}
