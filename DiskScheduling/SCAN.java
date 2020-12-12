package disk;

import java.util.Scanner;

public class SCAN
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

		for (int i = 0; i < num; i++)
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
		System.out.println("\n从" + start + "号磁道开始，向磁道号增加方向移动\n");

		bubbleSort(req);

		int firstIndex = -1;
		for (int i = 0; i < num; i++)
		{
			if (start > req[i])
				continue;

			firstIndex = i;
			break;
		}

		if (firstIndex != -1)
		{
			int index = firstIndex;
			for (int i = 0; i < num - index; i++)
			{
				visit[i] = req[firstIndex];
				move[i] = req[firstIndex] - start;
				start = visit[i];
				firstIndex++;
			}
			
			for(int i = index - 1, pos = num - index; i >= 0; i--, pos++)
			{
				visit[pos] = req[i];
				move[pos] = start - req[i];
				start = visit[pos];
			}
		}
		else
		{
			for(int i = 0; i < num; i++)
			{
				visit[i] = req[i];
				move[i] = Math.abs(req[i] - start);
				start = visit[i];
			}
		}
	}

	void bubbleSort(int arr[])
	{
		for (int i = 0; i < arr.length - 1; i++)
		{
			for (int j = 0; j < arr.length - 1 - i; j++)
			{
				if (arr[j] > arr[j + 1])
				{
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
	}

	void show()
	{
		System.out.println("被访问的\t\t移动距离\n下一个磁道号\t（磁道数）\n");
		for (int i = 0; i < num; i++)
		{
			System.out.println(visit[i] + "\t\t" + move[i]);
		}

		double sum = 0;

		for (int i : move)
		{
			sum += i;
		}

		System.out.println("平均寻道长度：" + sum / num);
	}

	public static void main(String[] args)
	{
		SCAN s = new SCAN();

		s.input();
		s.search();
		s.show();
	}
}
