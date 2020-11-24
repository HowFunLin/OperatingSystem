#include <stdio.h>
#include <stdlib.h>

struct sjf
{
    char name[10]; //������
    float arriveTime; //����ʱ��
    float startTime; //��ʼʱ��
    float serviceTime; //����ʱ��
    float finishTime; //���ʱ��
    float turnaroundTime; //��תʱ��
    float averageTurnaroundTime; //ƽ����תʱ��
    float weightedTurnaroundTime; //��Ȩ��תʱ��
    float averageWeightedTurnaroundTime; //ƽ����Ȩ��תʱ��
};

void input(struct sjf *p,int N)//������������Ϣ
{
    printf("�����������������ʱ�䡢����ʱ�䣺\n");
    printf("\n");
    for(int i=0; i<N; i++)
    {
        printf("���������%d����Ϣ:\n", i+1);
        printf("������: ");
        scanf("%s",p[i].name);
        printf("����ʱ��: ");
        scanf("%f",&p[i].arriveTime);
        printf("����ʱ��: ");
        scanf("%f",&p[i].serviceTime);
        printf("\n");
    }
}

void sort_arriveTime(struct sjf *p,int N) //�����н��̰��յ���ʱ���������
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

void sort_serviceTime(struct sjf *p,int N)//�Եȴ������еĽ��̰��շ���ʱ������
{
    struct sjf temp;
    int k=0;
    float end=0.0;//����CPU�Ѿ����е�ʱ��
    for(int i=0; i<N; i++)
    {
        k=i;//���浱ǰi���±�
        while(p[i].arriveTime<=end&&i<N)//�Դӵ�i��֮������н��̽�������
            i++;//�ҵ����һ���ȴ����̵��±꣨��ʵ���ǵ�ǰ�ڵȴ������һ�����̣�
        for (int t=k; t<i; t++)//�Ե�ǰ���ڵȴ�ִ�еĽ��̰��շ���ʱ������
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
        i=k; //��i���±껹ԭ
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
            //��תʱ��=Ҫ�����ʱ��+�ȴ�ʱ��
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
    printf("���ö̽��������㷨����̵�����˳��Ϊ��\n");
    for(i=0; i<N; i++)
    {
        printf("%s",p[i].name);
        if(i < N-1)
            printf("---->");
    }
    printf("\n\n");
    printf("����Ľ��̵�����ϢΪ\n");
    printf("������\t����ʱ��\t����ʱ��\t��תʱ��\t��Ȩ��תʱ��\n");
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
    int N; //��������

    printf("�����������Ŀ��");
    scanf("%d",&N);
    printf("\n");

    struct sjf a[N]; //����N���ṹ��
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
    printf("���ö̽��������㷨���ƽ����תʱ��Ϊ��%.1f \n",average_turnaroundTime);
    printf("���ö̽��������㷨���ƽ����Ȩ��תʱ��Ϊ��%.1f \n",average_weightedTurnaroundTime);
    return 0;
}
