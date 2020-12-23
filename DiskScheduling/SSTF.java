package disk;

import java.util.Scanner;

public class SSTF
{
	Scanner scan = new Scanner(System.in);
	
	int[] req;
	int num;
	int[] move;
	int[] visit;
	
	void input()
	{
		System.out.print("请输入进程数：");
		num = scan.nextInt();
		req = new int[num];
		move = new int[num];
		visit = new int[num];
		
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
	
		for(int j = 0; j < num; j++)
		{
			int min = Integer.MAX_VALUE;
			int index = -1;
			for(int i = 0; i < num; i++)
			{
				if(req[i] != -1)
				{
					int temp = Math.abs(start - req[i]);
					
					if(temp < min)
					{
						min = temp;
						index = i;
					}
				}
			}
			
			start = req[index];
			visit[j] = req[index];
			req[index] = -1;
			move[j] = min;
		}
	}
	
	void show()
	{
		System.out.println("被访问的\t\t移动距离\n下一个磁道号\t（磁道数）\n");
		for(int i = 0; i < num; i++)
		{
			System.out.println(visit[i] + "\t\t" + move[i]);
		}
		
		double sum = 0;
		
		for(int i : move)
		{
			sum += i;
		}
		
		System.out.println("平均寻道长度：" + sum / num);
	}
	
	SSTF()
	{
		System.out.println("-------最短寻道时间优先-------");
	}
	
	public static void main(String[] args)
	{
		SSTF sstf = new SSTF();
		
		sstf.input();
		sstf.search();
		sstf.show();
	}
}