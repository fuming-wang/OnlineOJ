import java.util.*;
//已自动为你导入 import java.util.* 如需需要其他，请自行导入
class Solution {
    public int fib(int n) {
        if(n==0) return 0;
        if(n==1) return 1;
        int a=0;
        int b=1;
        int c=1;
        for(int i=3;i<=n;i++){
            c=a+b;
            a=b;
            b=c;
        }
        return c;
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