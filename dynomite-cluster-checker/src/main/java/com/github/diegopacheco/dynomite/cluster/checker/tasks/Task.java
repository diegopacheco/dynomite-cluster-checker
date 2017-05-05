package com.github.diegopacheco.dynomite.cluster.checker.tasks;

import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionContext;

/**
 * Describes a DCC task.
 * 
 * @author diegopacheco
 *
 */
public interface Task {
	public void execute(ExecutionContext ec);
}
