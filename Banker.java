import java.util.LinkedHashSet;
import java.util.Scanner;

public class Banker
{
	int m; // 资源种类数
	int n; // 进程数

	int[] available;
	int[][] max;
	int[][] allocation;
	int[][] need;
	int[] request;

	Scanner scan = new Scanner(System.in);

	/**
	 * 初始化
	 */
	void init()
	{
		System.out.print("请输入资源种类数：");
		m = scan.nextInt();

		System.out.print("请输入进程数：");
		n = scan.nextInt();

		available = new int[m];
		max = new int[n][m];
		allocation = new int[n][m];
		need = new int[n][m];
		request = new int[m];

		System.out.println();

		System.out.println("请输入资源情况：");

		for (int j = 0; j < m; j++)
		{
			System.out.print("资源" + (char) (j + 65) + "的可利用情况：");
			available[j] = scan.nextInt();
		}

		System.out.println();

		for (int i = 0; i < n; i++)
		{
			System.out.println("进程P" + i + "的资源情况：");

			for (int j = 0; j < m; j++)
			{
				System.out.print("资源" + (char) (j + 65) + "的最大需求情况：");
				max[i][j] = scan.nextInt();
			}

			for (int j = 0; j < m; j++)
			{
				System.out.print("资源" + (char) (j + 65) + "的分配情况：");
				allocation[i][j] = scan.nextInt();
			}

			for (int j = 0; j < m; j++)
			{
				need[i][j] = max[i][j] - allocation[i][j];
			}

			System.out.println();
		}
	}

	/**
	 * 银行家算法
	 * 
	 * @return 算法是否成功执行
	 */
	boolean banker()
	{
		System.out.print("请输入发出请求的进程：P");
		int i = scan.nextInt();

		System.out.println();

		int[] av = new int[m];
		int[] al = new int[m];
		int[] ne = new int[m];

		System.out.println("输入请求向量的值：");
		for (int j = 0; j < m; j++)
		{
			System.out.print("资源" + (char) (j + 65) + "的请求情况：");
			request[j] = scan.nextInt();

			if (request[j] <= need[i][j])
			{
				if (request[j] <= available[j])
				{
					av[j] = available[j];
					al[j] = allocation[i][j];
					ne[j] = need[i][j];

					available[j] -= request[j];
					allocation[i][j] += request[j];
					need[i][j] -= request[j];
				} else
				{
					System.out.println("\n尚无足够资源，P" + i + "需等待！\n");
					return false;
				}
			} else
			{
				System.out.println("\n输入错误！需要的资源数已超过宣布的最大值！\n");
				return false;
			}
		}

		if (safety() == false) // 安全性检测
		{
			for (int j = 0; j < m; j++)
			{
				available[j] = av[j];
				allocation[i][j] = al[j];
				need[i][j] = ne[j];
			}

			System.out.println("\n系统处于不安全状态，P" + i + "需等待！\n");

			return false;
		}

		System.out.println("\n完成分配！\n");

		System.out.println("资源分配表：");
		System.out.println("Max\tAllocation\tNeed\tAvailable");

		for (i = 0; i < n; i++)
		{
			for (int j = 0; j < m; j++)
			{
				System.out.print(max[i][j] + " ");
			}
			System.out.print("\t");

			for (int j = 0; j < m; j++)
			{
				System.out.print(allocation[i][j] + " ");
			}
			System.out.print("\t\t");

			for (int j = 0; j < m; j++)
			{
				System.out.print(need[i][j] + " ");
			}
			System.out.print("\t");

			if (i == 0)
			{
				for (int j = 0; j < m; j++)
				{
					System.out.print(available[j] + " ");
				}
			}

			System.out.println();
		}

		System.out.println();

		return true;
	}

	/**
	 * 安全性算法
	 * 
	 * @return 系统是否处于安全状态
	 */
	boolean safety()
	{
		int[] work = new int[m];
		boolean[] finish = new boolean[n];

		for (int j = 0; j < m; j++)
			work[j] = available[j];

		for (int i = 0; i < n; i++)
			finish[i] = false;

		LinkedHashSet<Integer> set = new LinkedHashSet<Integer>();

		while (true)
		{
			boolean result = true;

			for (boolean f : finish)
			{
				result &= f;
			}

			if (result == false)
			{
				int index = findProgress(work, finish);

				if (index != -1)
				{
					set.add(index);
				} else
				{
					return false;
				}
			} else
				break;
		}

		System.out.print("\n安全序列为：");
		for (int i : set)
		{
			System.out.print("P" + i + " ");
		}
		System.out.println();

		return true;
	}

	/**
	 * 寻找符合条件的进程
	 * 
	 * @param work   工作向量
	 * @param finish 系统是否有足够的资源分配给进程
	 * @return 进程号
	 */
	int findProgress(int[] work, boolean[] finish)
	{
		int index = -1;

		pro: for (int i = 0; i < n; i++)
		{
			if (finish[i] == false)
			{
				for (int j = 0; j < m; j++)
				{
					if (need[i][j] > work[j])
						continue pro;
					if (j == m - 1 && need[i][j] <= work[j])
					{
						index = i;

						finish[i] = true;

						j = 0;

						for (; j < m; j++)
						{
							work[j] = work[j] + allocation[i][j];
						}

						break pro;
					}
				}
			}
		}

		return index;
	}

	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		Banker b = new Banker();

		int i = 1;

		b.init();
		while (true)
		{
			System.out.print("请选择您要执行的操作：1-请求资源 其他-退出 ");
			i = scan.nextInt();
			if (i != 1)
			{
				System.out.println("\n程序已退出！");
				break;
			}
			b.banker();
		}

		scan.close();
	}
}