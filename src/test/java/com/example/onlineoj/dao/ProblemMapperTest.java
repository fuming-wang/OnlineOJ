package com.example.onlineoj.dao;

import com.example.onlineoj.component.FileUtil;
import com.example.onlineoj.model.Problem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ProblemMapperTest {

    @Autowired
    private ProblemMapper problemMapper;

    @Test
    void add() {
        Problem problem=new Problem();
        problem.setId(0);
        problem.setTitle("两数之和");
        problem.setLevel("简单");
        problem.setDescription("给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。\n" +
                "\n" +
                "你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。\n" +
                "\n" +
                "你可以按任意顺序返回答案。\n" +
                "\n");

        problem.setTemplateCode("class Solution {\n" +
                "    public int[] twoSum(int[] nums, int target) {\n" +
                "\n" +
                "    }\n" +
                "}");

        problem.setTestCode("    public static void main(String[] args) {\n" +
                "        \n" +
                "    }");

        problemMapper.add(problem);
        problem.setTitle("两数之加");
        problem.setLevel("中等");
        problem.setDescription("给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。\n" +
                "\n" +
                "请你将两个数相加，并以相同形式返回一个表示和的链表。\n" +
                "\n" +
                "你可以假设除了数字 0 之外，这两个数都不会以 0 开头。\n" +
                "\n" +
                " \n" +
                "\n");
        problem.setTemplateCode("/**\n" +
                " * Definition for singly-linked list.\n" +
                " * public class ListNode {\n" +
                " *     int val;\n" +
                " *     ListNode next;\n" +
                " *     ListNode() {}\n" +
                " *     ListNode(int val) { this.val = val; }\n" +
                " *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }\n" +
                " * }\n" +
                " */\n" +
                "class Solution {\n" +
                "    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {\n" +
                "\n" +
                "    }\n" +
                "}");
        problemMapper.add(problem);
    }

    @Test
    void selectOne() {
        Problem problem=problemMapper.selectOne(1);
        System.out.println(problem);
        Problem problem1=problemMapper.selectOne(2);
        System.out.println(problem1);
    }

    @Test
    void selectAll() {
        List<Problem> list=problemMapper.selectAll();
        for(Problem p:list){
            System.out.println(p);
        }
    }


    @Test
    void selectOneInDetail() {
        Problem problem=problemMapper.selectOneInDetail(1);
        System.out.println(problem);
    }

    @Test
    void testUpdate() {
        Problem problem=new Problem();
        problem.setId(1);
        problem.setDescription("给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。\n" +
                "\n" +
                "你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。\n" +
                "\n" +
                "你可以按任意顺序返回答案。\n" +
                "\n" +
                "输入：nums = [2,7,11,15], target = 9\n" +
                "输出：[0,1]\n" +
                "解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。\n" +
                "\n" +
                "输入：nums = [3,2,4], target = 6\n" +
                "输出：[1,2]\n" +
                "\n" +
                "输入：nums = [3,3], target = 6\n" +
                "输出：[0,1]\n" +
                "\n" +
                "2 <= nums.length <= 10^9\n" +
                "-10^9 <= nums[i] <= 10^9\n" +
                "-10^9 <= target <= 10^9\n" +
                "只会存在一个有效答案\n" +
                "\n" +
                "全局变量请在函数内进行赋值");
        problemMapper.update(problem);
    }
}