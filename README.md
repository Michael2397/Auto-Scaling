This repository contains data and code for building and evaluating resource scheduling simulation, developed as part of:

Auto-Scaling Cloud Resources using LSTM and Reinforcement Learning to Improve Qos and Reduce Cost. Zhongjiang, Duansaisai, Liqing
------- 
(We regret to say that the size of Auto-Scaling is more than 100MB, exceeding github's maximum limitation. More code you can find from https://pan.baidu.com/s/1ezo7mP2oXHFjINcVbbLrNg )
 
 ###Contributions:
 1. We simulate resource schedule based on CloudSim
 2. We customize LSTM to predict incoming workload using historical workload effectively. (Because python is good at deep learning, LSTM network is realized by keras: https://github.com/Michael2397/MultivariateForecasting)
 3. We apply RL as a decision-maker that uses the historical the resource utilization and the predicted results in order to obtain the optimal action to scale in or scale out VMs in the planning phase.
 4. We conduct a series of experiments to evaluate the performance of proposed approach under real-world workload traces for different metrics compared with the other approaches.
 
 DataSource
 
 Experimental result
 
