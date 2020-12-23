package disk;

import java.util.Scanner;

public class FCFS
{
	Scanner scan = new Scanner(System.in);
	
	int[] req;
	int num;
	int[] move;
	
	void input()
	{
		System.out.print("请输入进程数：");
		num = scan.nextInt();
		req = new int[num];
		move = new int[num];
		
		for(int i = 0; i < num; i++)
		{
			System.out.print("进程" + (i + 1) + "访问的磁道号：");
			req[i] = scan.nextInt();
		}
		
		System.out.println();
	}
	
	void search()
	{
		System.out.print("请输入开始的磁道号：");
		int start = scan.nextInt();
		System.out.println("\n-------从" + start + "号磁道开始-------\n");
		
		for(int i = 0; i < num; i++)
		{
			move[i] = Math.abs(start - req[i]);
			start = req[i];
		}
	}
	
	void show()
	{
		System.out.println("被访问的\t\t移动距离\n下一个磁道号\t（磁道数）\n");
		for(int i = 0; i < num; i++)
		{
			System.out.println(req[i] + "\t\t" + move[i]);
		}
		
		double sum = 0;
		
		for(int i : move)
		{
			sum += i;
		}
		
		System.out.println("平均寻道长度：" + sum / num);
	}
	
	FCFS()
	{
		System.out.println("----------先来先服务----------");
	}
	
	public static void main(String[] args)
	{
		FCFS fcfs = new FCFS();
		
		fcfs.input();
		fcfs.search();
		fcfs.show();
	}
}