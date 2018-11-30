Auto-Scaling Cloud Resources using LSTM and Reinforcement Learning to Guarantee Service-Level Agreements and Reduce Resource Costs. Zhongjiang, Duansaisai, Liqing
====  
(We regret to say that the size of Auto-Scaling is more than 100MB, exceeding github's maximum limitation. More code you can find from https://pan.baidu.com/s/1ezo7mP2oXHFjINcVbbLrNg )
 
 Contributions:
------- 
 1. We simulate resource schedule based on CloudSim
 2. We customize LSTM to predict incoming workload using historical workload effectively. (Because python is good at deep learning, LSTM network is realized by keras: https://github.com/Michael2397/MultivariateForecasting)
 3. We apply RL as a decision-maker that uses the historical the resource utilization and the predicted results in order to obtain the optimal action to scale in or scale out VMs in the planning phase.
 4. We conduct a series of experiments to evaluate the performance of proposed approach under real-world workload traces for different metrics compared with the other approaches.
 
 DataSource
 ------- 
 To evaluate our approach, we used the NASA traces and the ClarkNet traces represents user’s requests respectively. You can find the processed data from https://pan.baidu.com/s/1CqZm2OjLWQzilus4MPyfSQ
 
 Experimental result
 -------
The two real-world workload patterns are shown：
![image](https://github.com/Michael2397/Auto-Scaling/blob/master/results/UserRequest.png)  

The CPU utilizations of the four approaches for NASA workloads at each interval. From the results, we observe that the LSTM approach has better results than other three approaches, but when the requests suddenly appear peak, there will be a shake, and the CPU utilizations even reach 120%. Although LSTM can get more accurate prediction results, it does not use empirical data in resource scheduling. The Linear approach also has the same  problem. While LSTMQL approach can get the stable results,  this is because RL decides whether the system needs to schedule VMs, avoiding unnecessary resource scheduling.
![image](https://github.com/Michael2397/Auto-Scaling/blob/master/results/CpuUtilization.png)  

The delay time at different intervals. In addition to the beginning of the result, the delay time of LSTMQL approach is almost zero, while other three approaches have varying degrees of delay
![image](https://github.com/Michael2397/Auto-Scaling/blob/master/results/TotalDelayTime.png)  

We can conclude that LSTMQL approach has fewer scheduling times, besides RL, the algorithm in execution phase also plays an important role in VM schedules. When requests suddenly appear peak, LSTMQL approach can immediately schedule the appropriate virtual machine in execution phase.
![image](https://github.com/Michael2397/Auto-Scaling/blob/master/results/TheNumberOfVMS.png)  

