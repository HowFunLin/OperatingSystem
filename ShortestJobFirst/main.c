#include <stdio.h>
#include <stdlib.h>

struct sjf
{
    char name[10]; //进程名
    float arriveTime; //到达时间
    float startTime; //开始时间
    float serviceTime; //服务时间
    float finishTime; //完成时间
    float turnaroundTime; //周转时间
    float averageTurnaroundTime; //平均周转时间
    float weightedTurnaroundTime; //带权周转时间
    float averageWeightedTurnaroundTime; //平均带权周转时间
};

void input(struct sjf *p,int N)//输入进程相关信息
{
    printf("请输入进程名、到达时间、服务时间：\n");
    printf("\n");
    for(int i=0; i<N; i++)
    {
        printf("请输入进程%d的信息:\n", i+1);
        printf("进程名: ");
        scanf("%s",p[i].name);
        printf("到达时间: ");
        scanf("%f",&p[i].arriveTime);
        printf("服务时间: ");
        scanf("%f",&p[i].serviceTime);
        printf("\n");
    }
}

void sort_arriveTime(struct sjf *p,int N) //对所有进程按照到达时间进行排序
{
    int i,j;
    struct sjf temp;
    for(i=0; i<N-1; i++)
    {
        for(j=0; j<N-i-1; j++)
        {
            if(p[j].arriveTime>p[j+1].arriveTime)
            {
                temp=p[j];
                p[j]=p[j+1];
                p[j+1]=temp;
            }
        }
    }
}

void sort_serviceTime(struct sjf *p,int N)//对等待队列中的进程按照服务时间排序
{
    struct sjf temp;
    int k=0;
    float end=0.0;//保存CPU已经运行的时间
    for(int i=0; i<N; i++)
    {
        k=i;//保存当前i的下标
        while(p[i].arriveTime<=end&&i<N)//对从第i个之后的所有进程进行搜索
            i++;//找到最后一个等待进程的下标（其实就是当前在等待的最后一个进程）
        for (int t=k; t<i; t++)//对当前正在等待执行的进程按照服务时间排序
        {
            for (int j = k+1; j<i; j++)
            {
                if(p[t].serviceTime < p[j].serviceTime)
                    continue;
                else
                {
                    temp = p[t];
                    p[t] = p[j];
                    p[j] = temp;
                }
            }
        }
        i=k; //将i的下标还原
        end+=p[i].serviceTime;
    }
}

void run(struct sjf *p,int N)
{
    int i;
    for(i=0; i<N; i++)
    {
        if(i==0)
        {
            p[i].startTime=p[i].arriveTime;
            p[i].finishTime=p[i].arriveTime+p[i].serviceTime;
            //周转时间=要求服务时间+等待时间
            p[i].turnaroundTime=p[i].serviceTime;
            p[i].weightedTurnaroundTime=p[i].turnaroundTime/p[i].serviceTime;
        }
        else
        {
            p[i].startTime=p[i-1].finishTime;
            p[i].finishTime=p[i].startTime+p[i].serviceTime;
            p[i].turnaroundTime=p[i].finishTime-p[i].arriveTime;
            p[i].weightedTurnaroundTime=p[i].turnaroundTime/p[i].serviceTime;
        }
    }
}

void show(struct sjf *p,int N)
{
    int i;
    printf("调用短进程优先算法后进程的运行顺序为：\n");
    for(i=0; i<N; i++)
    {
        printf("%s",p[i].name);
        if(i < N-1)
            printf("---->");
    }
    printf("\n\n");
    printf("具体的进程调度信息为\n");
    printf("进程名\t到达时间\t服务时间\t周转时间\t带权周转时间\n");
    for(i=0; i<N; i++)
    {
        printf("%3s \t",p[i].name);
        printf("%7.2f \t",p[i].arriveTime);
        printf("%7.2f \t",p[i].serviceTime);
        printf("%7.2f \t",p[i].turnaroundTime);
        printf("%7.2f \t",p[i].weightedTurnaroundTime);
        printf("\n");
    }
}

int main()
{
    int N; //进程数量

    printf("请输入进程数目：");
    scanf("%d",&N);
    printf("\n");

    struct sjf a[N]; //创建N个结构体
    input(a,N);
    sort_arriveTime(a,N);
    sort_serviceTime(a,N);
    run(a,N);
    show(a,N);
    printf("\n");

    int i;
    float average_turnaroundTime=0;
    float average_weightedTurnaroundTime=0;
    for(i=0; i<N; i++)
    {
        average_turnaroundTime+=a[i].turnaroundTime;
        average_weightedTurnaroundTime+=a[i].weightedTurnaroundTime;
    }
    average_turnaroundTime/=N;
    average_weightedTurnaroundTime/=N;
    printf("采用短进程优先算法算得平均周转时间为：%.1f \n",average_turnaroundTime);
    printf("采用短进程优先算法算得平均带权周转时间为：%.1f \n",average_weightedTurnaroundTime);
    return 0;
}
