package mykidong.storm.dao.hazelcast;

import mykidong.storm.api.dao.GlobalUniqueIdDao;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IdGenerator;

public class HazelcastGlobalUniqueIdDao implements GlobalUniqueIdDao {

	@Override
	public long getGlobalUniqueId(String idName) {
		IdGenerator idGenerator = Hazelcast.getIdGenerator(idName);

		return idGenerator.newId();
	}
}
