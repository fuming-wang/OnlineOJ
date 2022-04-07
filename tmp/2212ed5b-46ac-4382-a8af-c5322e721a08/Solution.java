import java.util.*;
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> hashtable = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; ++i) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[]{hashtable.get(target - nums[i]), i};
            }
            hashtable.put(nums[i], i);
        }
        return new int[0];
    }
    public static void main(String[] args) {
        Solution solution=new Solution();
        int[] num1=new int[]{2,7,11,15};
        int[] ret1=solution.twoSum(num1,9);
        int[] ans1={0,1};
        boolean isTrue1=solution.isSame(ans1,ret1);
        if(isTrue1){
            System.out.println("case1 is Ok");
        }else{
            System.out.println("case1 is failed");
            System.exit(0);
        }
        int[] num2=new int[]{3,2,4};
        int[] ret2=solution.twoSum(num2,6);
        int[] ans2={1,2};
        boolean isTrue2=solution.isSame(ans2,ret2);
        if(isTrue2){
            System.out.println("case2 is Ok");
        }else{
            System.out.println("case2 is failed");
            System.exit(0);
        }
        int[] num3=new int[]{3,3};
        int[] ret3=solution.twoSum(num2,6);
        int[] ans3={1,2};
        boolean isTrue3=solution.isSame(ans3,ret3);
        if(isTrue3){
            System.out.println("case3 is Ok");
        }else{
            System.out.println("case3 is failed");
            System.exit(0);
        }
        System.out.println("All Yes");
    }
    private boolean isSame(int[] arr1,int[] arr2){
        if(arr1.length!=arr2.length){
            return false;
        }
        for(int i=0;i<arr1.length;i++){
            if(arr1[i]!=arr2[i]){
                return false;
            }
        }
        return true;
    }
}