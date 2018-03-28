package com.github.diegopacheco.dynomite.cluster.config;

import java.util.LinkedList;
import java.util.List;

import com.github.diegopacheco.dynomite.cluster.checker.cluster.cache.DynoClientCache;
import com.github.diegopacheco.dynomite.cluster.checker.cluster.cache.RedisClientCache;
import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionContext;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.CheckClusterFailoverTask;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.CheckDataReplicationTask;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.CheckNodesConnectivityTask;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.CleanUpTask;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.GetJsonReportResultTask;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.RedisReplicationTask;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.SetupTask;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.Task;
import com.github.diegopacheco.dynomite.cluster.checker.util.Chronometer;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.name.Names;

/**
 * Guice module.
 * 
 * @author diegopacheco
 *
 */
public class GuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Chronometer.class);
		bind(ExecutionContext.class);
		bind(DynoClientCache.class).asEagerSingleton();
		bind(RedisClientCache.class).asEagerSingleton();
		bind(GetJsonReportResultTask.class).asEagerSingleton();
		bind(List.class).annotatedWith(Names.named("tasks")).toProvider(TasksProvider.class);
	}

	public static class TasksProvider implements Provider<List<Task>> {
		public List<Task> get() {
			List<Task> tasks = new LinkedList<>();
			tasks.add(new SetupTask());
			tasks.add(new CheckNodesConnectivityTask());
			tasks.add(new CheckDataReplicationTask());
			tasks.add(new CheckClusterFailoverTask());
			tasks.add(new RedisReplicationTask());
			tasks.add(new CleanUpTask());
			return tasks;
		}
	}

}
