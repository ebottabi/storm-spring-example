package mykidong.storm.service;

import mykidong.storm.api.dao.GlobalUniqueIdDao;
import mykidong.storm.api.service.GlobalUniqueIdService;

public class GlobalUniqueIdServiceImpl implements GlobalUniqueIdService {
	
	private GlobalUniqueIdDao globalUniqueIdDao;	

	public void setGlobalUniqueIdDao(GlobalUniqueIdDao globalUniqueIdDao) {
		this.globalUniqueIdDao = globalUniqueIdDao;
	}


	@Override
	public long getGlobalUniqueId(String idName) {		
		return this.globalUniqueIdDao.getGlobalUniqueId(idName);
	}

}
