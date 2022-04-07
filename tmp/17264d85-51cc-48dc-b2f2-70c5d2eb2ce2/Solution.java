import java.util.*;
//已自动为你导入 import java.util.* 如需需要其他，请自行导入
class Solution {
    public int fib(int n) {
        int[] dp=new int[n+1];
        dp[1]=1;
        for(int i=2;i<=n;i++){
            dp[i]=dp[i-1]+dp[i-2];
        }
        return dp[n];
    }
    public static void main(String[] args) {
        Solution solution=new Solution();
        int[] dp=solution.getFib();
        for(int i=1;i<31;i++){
            int ret=solution.fib(i);
            if(ret==dp[i]){
                System.out.println("case"+i+"is OK");
            }else{
                System.out.println("case"+i+"is failed");
                System.out.println("最后执行的测试数据"+i+"\n期望的结果"+dp[i]+"\n你的结果"+ret);
                break;
            }
        }
    }
    private int[] getFib(){
        int[] dp=new int[31];
        dp[0]=0;
        dp[1]=1;
        for(int i=2;i<31;i++){
            dp[i]=dp[i-1]+dp[i-2];
        }
        return dp;
    }
}