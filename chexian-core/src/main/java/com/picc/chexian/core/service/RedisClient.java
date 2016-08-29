package com.picc.chexian.core.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * redis 客户端实现
 * 
 * @author zc
 * @version Revision Date
 */
@Service
public class RedisClient {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "readRedisPool")
	protected JedisPool readRedisPool;

	@Resource(name = "writeRedisPool")
	protected JedisPool writeRedisPool;
	
	@Resource(name = "mqRedisPool")
	protected JedisPool mqRedisPool;

	private Serializable getObjectFromBytes(byte[] objBytes) {
		if (objBytes == null || objBytes.length == 0) {
			return null;
		}
		try {
			ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
			ObjectInputStream oi = new ObjectInputStream(bi);
			return (Serializable) oi.readObject();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return null;

	}

	private byte[] getBytesFromObject(Serializable obj) {
		if (obj == null) {
			return null;
		}
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			return bo.toByteArray();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#addList(java.lang.String,
	 * java.util.List)
	 */

	public Boolean addList(String key, List<Serializable> list) {
		if (key != null && list != null && list.size() > 0) {
			logger.trace("key:{} addList  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				Pipeline pl = writeRedisClient.pipelined();
				if (pl != null) {
					for (Serializable s : list) {
						if (s == null) {
							logger.warn(" Serializable is null !");
							continue;
						}
						try {
							pl.rpush(key.getBytes(), getBytesFromObject(s));
						} catch (RuntimeException re) {
							logger.error("rpush begin error ! key {}", key);
							logger.error("rpush begin error !", re);
						}
					}
					pl.sync();
					tag = true;
				} else {
					logger.error("pl couldn't be null !");
				}
				try {
					pl = null;
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} addList   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#addListItems(java.lang.String,
	 * java.io.Serializable[])
	 */

	public Boolean addListItems(String key, Serializable[] values) {
		if (key != null && values != null && values.length > 0) {
			logger.trace("key:{} addListItems  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				Pipeline pl = writeRedisClient.pipelined();
				if (pl != null) {
					for (Serializable s : values) {
						if (s == null) {
							logger.warn(" Serializable is null !");
							continue;
						}
						try {
							pl.rpush(key.getBytes(), getBytesFromObject(s));
						} catch (RuntimeException re) {
							logger.error("rpush begin error ! key {}", key);
							logger.error("rpush begin error !", re);
						}
					}
					pl.sync();
					tag = true;
				} else {
					logger.error("pl couldn't be null !");
				}
				try {
					pl = null;
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} addListItems   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#addMap(java.lang.String,
	 * java.util.Map)
	 */

	public Boolean addMap(String key, Map<String, Serializable> map) {
		if (key != null && map != null && map.size() > 0) {
			logger.trace("key:{} addMap  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				Set<String> mapkey = map.keySet();
				Map<byte[], byte[]> hash = new HashMap<byte[], byte[]>();
				for (String k : mapkey) {
					Serializable value = map.get(k);
					if (value == null) {
						logger.warn(" map.get(k:{}) is null !", k);
						continue;
					}
					hash.put(k.getBytes(), getBytesFromObject(value));
				}
				if (hash.size() > 0) {
					try {
						writeRedisClient.hmset(key.getBytes(), hash);
						tag = true;
					} catch (RuntimeException re) {
						logger.error("hset begin error ! key {},map{}", key, hash);
						logger.error("hset begin error !", re);
					}
				}
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} addMap   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#addMapValue(java.lang.String,
	 * java.lang.String, java.io.Serializable)
	 */

	public Boolean addMapValue(String key, String mapkey, String value) {
		if (key != null && mapkey != null) {
			logger.trace("key:{} addMapValue  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					writeRedisClient.hset(key, mapkey, value);
					tag = true;
				} catch (RuntimeException re) {
					logger.error("hset begin error ! key {},mapkey{}", key, mapkey);
					logger.error("hset begin error !", re);
				}
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} addMapValue   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#addSet(java.lang.String,
	 * java.util.Set)
	 */

	public Boolean addSet(String key, Set<Serializable> set) {
		if (key != null && set != null && set.size() > 0) {
			logger.trace("key:{} addSet  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				Pipeline pl = writeRedisClient.pipelined();
				if (pl != null) {
					for (Serializable s : set) {
						if (s == null) {
							logger.warn(" Serializable is null !");
							continue;
						}
						try {
							pl.sadd(key.getBytes(), getBytesFromObject(s));
						} catch (RuntimeException re) {
							logger.error("sadd begin error ! key {}", key);
							logger.error("sadd begin error !", re);
						}
					}
					pl.sync();
					tag = true;
				} else {
					logger.error("pl couldn't be null !");
				}
				try {
					pl = null;
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} addSet   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#addSetItems(java.lang.String,
	 * java.io.Serializable[])
	 */

	public Boolean addSetItems(String key, String... values) {
		if (key != null && values != null && values.length > 0) {
			logger.trace("key:{} addSetItems  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				writeRedisClient.sadd(key, values);
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
				logger.trace("key:{} addSetItems   end!", key);
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} addSetItems   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty !");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#delList(java.lang.String)
	 */

	public Boolean delList(String... key) {
		if (key != null && key.length > 0) {
			logger.trace("key:{} delList  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					byte[][] bytes = new byte[key.length][];

					for (int j = 0; j < key.length; j++) {
						byte[] b = key[j].getBytes();

						bytes[j] = b;
					}

					writeRedisClient.del(bytes);

					tag = true;
				} catch (RuntimeException re) {
					logger.error("del begin error ! key {}", key);
					logger.error("del begin error !", re);
				}
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} delList   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or empty !");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#delListItems(java.lang.String,
	 * java.lang.Integer[])
	 */

	public Boolean delListItems(String key, Integer[] indexes) {
		if (key != null && indexes != null && indexes.length > 0) {
			logger.trace("key:{} delListItems indexes  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					List<byte[]> valueList = new ArrayList<byte[]>();
					for (Integer index : indexes) {
						try {
							valueList.add(writeRedisClient.lindex(key.getBytes(), index));
						} catch (RuntimeException re) {
							logger.error("lrem begin error ! key {}", key);
							logger.error("lrem begin error !", re);
						}
					}
					for (byte[] value : valueList) {
						try {
							writeRedisClient.lrem(key.getBytes(), 1, value);
						} catch (RuntimeException re) {
							logger.error("lrem begin error ! key {}", key);
							logger.error("lrem begin error !", re);
						}
					}
					tag = true;
				} catch (RuntimeException re) {
					logger.error("del begin error ! key {}", key);
					logger.error("del begin error !", re);
				}
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} delListItems indexes   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#delListItems(java.lang.String,
	 * java.lang.Integer[])
	 */

	public Boolean delListItems(String key, Serializable[] values) {
		if (key != null && values != null && values.length > 0) {
			logger.trace("key:{} delListItems values  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					for (Serializable v : values) {
						if (v != null) {
							try {
								writeRedisClient.lrem(key.getBytes(), 1, getBytesFromObject(v));
							} catch (RuntimeException re) {
								logger.error("lrem begin error ! key {}", key);
								logger.error("lrem begin error !", re);
							}
						} else {

						}

					}
					tag = true;
				} catch (RuntimeException re) {
					logger.error("del begin error ! key {}", key);
					logger.error("del begin error !", re);
				}
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} delListItems   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#delMap(java.lang.String,
	 * java.util.Map)
	 */

	public Boolean delMap(String... key) {
		if (key != null) {
			logger.trace("key:{} delMap  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					byte[][] bytes = new byte[key.length][];

					for (int j = 0; j < key.length; j++) {
						byte[] b = key[j].getBytes();

						bytes[j] = b;
					}

					writeRedisClient.del(bytes);
					tag = true;
				} catch (RuntimeException re) {
					logger.error("delMap begin error ! key {}", key);
					logger.error("delMap begin error !", re);
				}
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} delMap   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null ");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#delMapValue(java.lang.String,
	 * java.lang.String)
	 */

	public Boolean delMapValue(String key, String mapkey) {
		if (key != null && mapkey != null) {
			logger.trace("key:{} delMapValue  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					writeRedisClient.hdel(key.getBytes(), mapkey.getBytes());

					tag = true;
				} catch (RuntimeException re) {
					logger.error("del begin error ! key {}", key);
					logger.error("del begin error !", re);
				}
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} delMapValue   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null ");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#delSet(java.lang.String,
	 * java.io.Serializable)
	 */

	public Boolean delSet(String... key) {
		if (key != null) {
			logger.trace("key:{} delSet  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {

					byte[][] bytes = new byte[key.length][];

					for (int j = 0; j < key.length; j++) {
						byte[] b = key[j].getBytes();

						bytes[j] = b;
					}

					writeRedisClient.del(bytes);
					tag = true;
				} catch (RuntimeException re) {
					logger.error("del begin error ! key {}", key);
					logger.error("del begin error !", re);
				}
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} delSet   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null ");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#delSetItems(java.lang.String,
	 * java.io.Serializable[])
	 */

	public Boolean delSetItems(String key, String... values) {
		if (key != null && values != null && values.length > 0) {
			logger.trace("key:{} delSetItems  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				writeRedisClient.srem(key, values);
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
				return tag;
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} delSetItems   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null !");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#getList(java.lang.String)
	 */

	public long getListLength(String key) {
		Long size = 0L;
		if (key != null) {
			logger.trace("key:{} getList  begin !", key);
			Jedis readRedisClient = null;
			try {
				readRedisClient = (Jedis) readRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (readRedisClient != null) {
				size = readRedisClient.llen(key.getBytes());
				if (size == null || size < 0) {
					size = 0L;
					logger.error("key={}  list not existing !", key);
				}
				try {
					readRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool couldn't return  connection !");
			}
			logger.trace("key:{} getList   end!", key);
		} else {
			throw new IllegalArgumentException("param key is null ");
		}
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#getList(java.lang.String)
	 */

	public List<Serializable> getList(String key) {
		if (key != null) {
			logger.trace("key:{} getList  begin !", key);
			Jedis readRedisClient = null;
			List<Serializable> result = null;
			try {
				readRedisClient = (Jedis) readRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (readRedisClient != null) {
				Long size = readRedisClient.llen(key.getBytes());
				if (size != null && size > 0) {
					List<byte[]> results = readRedisClient.lrange(key.getBytes(), 0,
							size.intValue());
					if (results != null && results.size() > 0) {
						result = new LinkedList<Serializable>();
						for (byte[] s : results) {
							if (s != null) {
								Serializable res = getObjectFromBytes(s);
								result.add(res);
							} else {
								result.add(null);
							}
						}
					}
				} else {
					logger.error("key={}  list not existing !", key);
				}
				try {
					readRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool couldn't return  connection !");
			}
			logger.trace("key:{} getList   end!", key);
			return result;
		} else {
			throw new IllegalArgumentException("param key is null ");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#getList(java.lang.String)
	 */

	public List<Serializable> getList(String key, int start, int end) {
		if (key != null) {
			logger.trace("key:{}, start:{}, end:{} getList  begin !", new Object[] { key, start,
					end });
			Jedis readRedisClient = null;
			List<Serializable> result = null;
			try {
				readRedisClient = (Jedis) readRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (readRedisClient != null) {
				List<byte[]> results = readRedisClient.lrange(key.getBytes(), start, end);
				if (results != null && results.size() > 0) {
					result = new LinkedList<Serializable>();
					for (byte[] s : results) {
						if (s != null) {
							Serializable res = getObjectFromBytes(s);
							result.add(res);
						} else {
							result.add(null);
						}
					}
				} else {
					logger.error("key={},start={},end={}  list not existing !", new Object[] { key,
							start, end });
				}

				try {
					readRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool couldn't return  connection !");
			}
			logger.trace("key:{}, start={},end={} getList   end!", new Object[] { key, start, end });
			return result;
		} else {
			throw new IllegalArgumentException("param key is null ");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#getMap(java.lang.String)
	 */

	public Map<String, String> getMap(String key) {
		if (key != null) {
			logger.trace("key:{} getMap  begin !", key);
			Jedis readRedisClient = null;
			Map<String, String> result = null;
			try {
				readRedisClient = (Jedis) readRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (readRedisClient != null) {
				result = readRedisClient.hgetAll(key);
				try {
					readRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool couldn't return  connection !");
			}
			logger.trace("key:{} getMap   end!", key);
			return result;
		} else {
			throw new IllegalArgumentException("param key is null ");
		}
	}

	public Serializable[] getMapItems(String key, String[] fields) {
		if (key == null) {
			throw new IllegalArgumentException("param key is null ");
		}
		if (fields == null || fields.length < 1) {
			throw new IllegalArgumentException("param fields is empty ");
		}
		logger.trace("key:{} getMap  begin !", key);
		Jedis readRedisClient = null;
		try {
			readRedisClient = (Jedis) readRedisPool.getResource();
		} catch (JedisConnectionException e) {
			logger.error("redis connection pool havn't idle contention !", e);
		}

		Serializable[] result = null;
		if (readRedisClient != null) {
			Map<byte[], byte[]> resultMap = readRedisClient.hgetAll(key.getBytes());
			if (resultMap != null && !resultMap.isEmpty()) {
				result = new Serializable[fields.length];
				for (int i = 0; i < fields.length; i++) {
					byte[] v = resultMap.get(fields[i].getBytes());
					if (v == null || "".equals(v)) {
						continue;
					}
					result[i] = getObjectFromBytes(v);
				}
			}
		}

		try {
			readRedisClient.close();
		} catch (RuntimeException e) {
			logger.error("redis connection pool couldn't return  connection !", e);
		}

		return result;
	}

	public String getMapItem(String key, String field) {
		if (key == null) {
			throw new IllegalArgumentException("param key is null ");
		}
		if (field == null) {
			throw new IllegalArgumentException("param field is null ");
		}

		logger.trace("key:{} getMap  begin !", key);
		Jedis readRedisClient = null;
		try {
			readRedisClient = (Jedis) readRedisPool.getResource();
		} catch (JedisConnectionException e) {
			logger.error("redis connection pool havn't idle contention !", e);
		}

		String result = null;

		if (readRedisClient != null) {
			result = readRedisClient.hget(key, field);
		}

		try {
			readRedisClient.close();
		} catch (RuntimeException e) {
			logger.error("redis connection pool couldn't return  connection !", e);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#getSet(java.lang.String)
	 */

	public Set<String> getSet(String key) {
		if (key != null) {
			logger.trace("key:{} getSet  begin !", key);
			Jedis readRedisClient = null;
			Set<String> result = null;
			try {
				readRedisClient = (Jedis) readRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (readRedisClient != null) {
				result = readRedisClient.smembers(key);
				try {
					readRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool couldn't return  connection !");
			}
			logger.trace("key:{} getSet   end!", key);
			return result;
		} else {
			throw new IllegalArgumentException("param key is null ");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aipi.core.redis.IRedisClient#setListItems(java.lang.String,
	 * java.lang.Integer[], java.io.Serializable[])
	 */

	public Boolean setListItems(String key, Integer[] indexes, Serializable[] values) {
		if (key != null && values != null && values.length > 0 && indexes != null
				&& indexes.length > 0 && indexes.length == values.length) {
			logger.trace("key:{} setListItems  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				Pipeline pl = writeRedisClient.pipelined();
				if (pl != null) {
					for (int i = 0; i < indexes.length; i++) {
						Integer index = indexes[i];
						Serializable value = values[i];
						if (index != null && value != null) {
							try {
								pl.lset(key.getBytes(), index, getBytesFromObject(value));
							} catch (RuntimeException re) {
								logger.error("lpush begin error ! key {}", key);
								logger.error("lpush begin error !", re);
							}
						}
					}
					pl.sync();
					try {
						pl = null;
						writeRedisClient.close();
					} catch (RuntimeException e) {
						logger.error("redis connection pool couldn't return  connection !", e);
					}
				} else {
					logger.error("pl  couldn't be  null !");
				}
			} else {
				logger.error("redis connection pool couldn't return  connection !");
			}
			logger.trace("key:{} setListItems  end !", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
	}

	public Set<String> getKeysSet(String pattern) {
		if (pattern == null || pattern.length() < 1) {
			throw new IllegalArgumentException("param pattern is null or pattern is empty");
		}
		logger.trace("fetch keys by pattern {} !", pattern);
		Jedis readRedisClient = null;
		Set<byte[]> result = null;
		Set<String> rresult = null;
		try {
			readRedisClient = (Jedis) readRedisPool.getResource();
		} catch (JedisConnectionException e) {
			logger.error("redis connection pool havn't idle contention !", e);
		}
		if (readRedisClient != null) {
			try {
				result = readRedisClient.keys(pattern.getBytes());
				if (result != null && !result.isEmpty()) {
					rresult = new HashSet<String>();
					for (byte[] v : result) {
						rresult.add((String) getObjectFromBytes(v));
					}
				}

			} catch (RuntimeException re) {
				logger.error("keys  begin error ! pattern {}", pattern);
				logger.error("keys begin error !", re);
			}
			try {
				readRedisClient.close();
			} catch (RuntimeException e) {
				logger.error("redis connection pool couldn't return  connection !", e);
			}
		} else {
			logger.error("redis connection pool couldn't return  connection !");
		}
		logger.trace("fetch keys by pattern {} end !", pattern);
		return rresult;
	}

	// 1 成功 0 失败

	public Long expire(String key, int seconds) {
		if (key == null || key.length() < 1) {
			throw new IllegalArgumentException("param key is null or key is empty");
		}
		logger.trace("set key: {} timeout: {}s  begin !", new Object[] { key, seconds });
		Jedis writeRedisClient = null;
		Long result = 0L;
		try {
			writeRedisClient = (Jedis) writeRedisPool.getResource();
		} catch (JedisConnectionException e) {
			logger.error("redis connection pool havn't idle contention !", e);
		}
		if (writeRedisClient != null) {
			try {
				result = writeRedisClient.expire(key.getBytes(), seconds);
				logger.trace("set key: {} timeout  end!", key);
			} catch (RuntimeException re) {
				logger.error("set key: {} timeout error !", key);
			}
			try {
				writeRedisClient.close();
			} catch (RuntimeException e) {
				logger.error("redis connection pool couldn't return  connection !", e);
			}
		} else {
			logger.error("redis connection pool couldn't return  connection !");
		}
		return result;
	}

	public Long getNumber(String key) {
		if (key != null) {
			logger.trace("key:{} getCount  begin !", key);
			Jedis readRedisClient = null;
			Long result = null;
			try {
				readRedisClient = (Jedis) readRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (readRedisClient != null) {
				try {
					byte[] bresult = readRedisClient.get(key.getBytes());
					if (bresult != null && bresult.length > 0) {
						result = (Long) getObjectFromBytes(bresult);
					} else {
						logger.error("key={}  set not existing !", key);
					}
				} catch (RuntimeException e) {
					logger.error("get key: {} begin error !", key);
					logger.error("error  info {} !", e);
				}
				try {
					readRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool couldn't return  connection !");
			}
			logger.trace("key:{} getCount   end!", key);
			return result;
		} else {
			throw new IllegalArgumentException("param key is null ");
		}
	}

	public Long setNumber(String key, Long number) {
		if (key != null) {
			logger.trace("key:{} setCount[{}]  begin !", key, number);
			Jedis writeRedisClient = null;
			Long result = null;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					writeRedisClient.set(key.getBytes(), getBytesFromObject(number));
					String resultStr = number.toString();
					if (resultStr != null && resultStr.length() > 0) {
						result = Long.parseLong(resultStr);
					} else {
						logger.error("key={}  set not existing !", key);
					}
				} catch (RuntimeException e) {
					logger.error("get key: {} begin error !", key);
					logger.error("error  info {} !", e);
				}
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool couldn't return  connection !");
			}
			logger.trace("key:{} setCount[{}]   end!", key, number);
			return result;
		} else {
			throw new IllegalArgumentException("param key is null ");
		}
	}

	public Long incrby(String key, long incr) {
		if (key != null) {
			logger.trace("key:[{}]incrby incr:[{}] begin !", key, incr);
			Jedis writeRedisClient = null;
			Long result = null;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					result = writeRedisClient.incrBy(key.getBytes(), incr);
					if (result != null) {
					} else {
						logger.error("key={}  set not existing !", key);
					}
				} catch (RuntimeException e) {
					logger.error("incrBy key:[{}] incrby incr:[{}] begin error !", key, incr);
					logger.error("error  info {} !", e);
				}
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool couldn't return  connection !");
			}
			logger.trace("key:[{}]incrby incr:[{}] end !", key, incr);
			return result;
		} else {
			throw new IllegalArgumentException("param key is null ");
		}
	}

	public Long decrby(String key, long decr) {
		if (key != null) {
			logger.trace("key:[{}]decrby incr:[{}] begin !", key, decr);
			Jedis writeRedisClient = null;
			Long result = null;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					result = writeRedisClient.decrBy(key.getBytes(), decr);
					if (result != null) {
					} else {
						logger.error("key={}  set not existing !", key);
					}
				} catch (RuntimeException e) {
					logger.error("decrby key:[{}] decr :[{}] begin error !", key, decr);
					logger.error("error  info {} !", e);
				}
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool couldn't return  connection !");
			}
			logger.trace("key:[{}]decrby incr:[{}] end !", key, decr);
			return result;
		} else {
			throw new IllegalArgumentException("param key is null ");
		}
	}

	public Long persist(String key) {
		if (key != null) {
			logger.trace("key:[{}] persist begin !", key);
			Jedis writeRedisClient = null;
			Long result = null;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					result = writeRedisClient.persist(key.getBytes());
				} catch (RuntimeException e) {
					logger.error("persist key:[{}]  begin error !", key);
					logger.error("error  info {} !", e);
				}
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool couldn't return  connection !");
			}
			logger.trace("key:[{}] persist end !", key);
			return result;
		} else {
			throw new IllegalArgumentException("param key is null ");
		}
	}

	public Long ttl(String key) {
		if (key != null) {
			logger.trace("key:[{}] ttl begin !", key);
			Jedis readRedisClient = null;
			Long result = null;
			try {
				readRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (readRedisClient != null) {
				try {
					result = readRedisClient.ttl(key);
				} catch (RuntimeException e) {
					logger.error("ttl key:[{}]  begin error !", key);
					logger.error("error  info {} !", e);
				}
				try {
					readRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool couldn't return  connection !");
			}
			logger.trace("key:[{}] ttl end !", key);
			return result;
		} else {
			throw new IllegalArgumentException("param key is null ");
		}
	}

	public boolean exists(String key) {
		boolean flag = false;
		if (key != null && !"".equals(key)) {
			logger.trace("exists key {} !", key);
			Jedis readRedisClient = null;
			try {
				readRedisClient = (Jedis) readRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (readRedisClient != null) {
				try {
					flag = readRedisClient.exists(key.getBytes());
				} catch (RuntimeException re) {
					logger.error("exists key error ! pattern {}", key);
					logger.error("exists key error !", re);
				}
				try {
					readRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool couldn't return  connection !");
			}
		}
		logger.trace("exists key {} end !", key);

		return flag;
	}

	public boolean hexists(String key, String field) {
		boolean flag = false;
		if (key != null && !"".equals(key)) {
			logger.trace("exists key {}, filed {}  !", key, field);
			Jedis readRedisClient = null;
			try {
				readRedisClient = (Jedis) readRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (readRedisClient != null) {
				try {
					flag = readRedisClient.hexists(key.getBytes(), field.getBytes());
				} catch (RuntimeException re) {
					logger.error("exists key {}, filed {}", key, field);
					logger.error("exists key error !", re);
				}
				try {
					readRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return  connection !", e);
				}
			} else {
				logger.error("redis connection pool couldn't return  connection !");
			}
		}
		logger.trace("exists key {}, filed {} end !", key, field);

		return flag;
	}

	/**
	 * 保存一个序列化对象到内存中
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public boolean setObject(String key, Serializable obj) {
		if (!StringUtils.isEmpty(key) && obj != null) {
			logger.trace("key:{} setObject:{} begin !", key, obj);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					writeRedisClient.set(key.getBytes("UTF-8"), getBytesFromObject(obj));
				} catch (Exception re) {
					logger.error("set begin error ! key {}", key);
					logger.error("set begin error !", re);
				}
				tag = true;
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} setObject:{} end!", key, obj);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
	}

	/**
	 * 获取一个序列化对象到内存中
	 * 
	 * @param key
	 * @return
	 */
	public Serializable getObject(String key) {
		Serializable obj = null;
		if (!StringUtils.isEmpty(key)) {
			logger.trace("key:{} getObject  begin !", key);
			Jedis readRedisClient = null;
			try {
				readRedisClient = (Jedis) readRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (readRedisClient != null) {
				try {
					byte[] bs = readRedisClient.get(key.getBytes("UTF-8"));
					obj = getObjectFromBytes(bs);
				} catch (Exception re) {
					logger.error("get begin error ! key {}", key);
					logger.error("get begin error !", re);
				}
				try {
					readRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} getObject, value:{}  end!", key, obj);
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
		return obj;
	}

	/**
	 * 取出所有关于key相关的key名称
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> keys(String key) {
		Set<String> setstring = null;
		if (!StringUtils.isEmpty(key)) {
			logger.trace("key:{} setObject  begin !", key);
			Jedis readRedisClient = null;
			try {
				readRedisClient = (Jedis) readRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (readRedisClient != null) {
				try {
					setstring = readRedisClient.keys("*" + key + "*");
				} catch (Exception re) {
					logger.error("set begin error ! key {}", key);
					logger.error("set begin error !", re);
				}
				try {
					readRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} keys   end!", key);
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
		return setstring;
	}

	/**
	 * 根据key删除缓存
	 * 
	 * @param key
	 * @return
	 */
	public boolean delByKey(String key) {
		if (!StringUtils.isEmpty(key)) {
			logger.trace("key:{} delByKey  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					writeRedisClient.del(key);
				} catch (Exception re) {
					logger.error("set begin error ! key {}", key);
					logger.error("set begin error !", re);
				}
				tag = true;
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} delByKey   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
	}

	/**
	 * 根据一组key删除缓存
	 * 
	 * @param key
	 * @return
	 */
	public boolean delByKey(String... keys) {
		if (null != keys && keys.length > 0) {
			logger.trace("keys:{} delByKey  begin !", keys);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					writeRedisClient.del(keys);
				} catch (Exception re) {
					logger.error("del begin error ! keys {}", keys);
					logger.error("del begin error !", re);
				}
				tag = true;
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("keys:{} delByKey   end!", keys);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
	}

	/**
	 * 根据key获取字符串
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		String obj = null;
		if (!StringUtils.isEmpty(key)) {
			logger.trace("key:{} getString  begin !", key);
			Jedis readRedisClient = null;
			try {
				readRedisClient = (Jedis) readRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (readRedisClient != null) {
				try {
					byte[] bs = readRedisClient.get(key.getBytes("UTF-8"));
					if (null != bs && bs.length > 0) {
						obj = new String(bs, "UTF-8");
					}
				} catch (Exception re) {
					logger.error("getString begin error ! key {}", key);
					logger.error("getString begin error !", re);
				}
				try {
					readRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} getString   end!", key);
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
		return obj;
	}

	/**
	 * 根据key存放value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setString(String key, String value) {
		if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
			logger.trace("key:{} setString  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			long startTime = System.currentTimeMillis();
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			long endTime1 = System.currentTimeMillis();
			logger.trace("1 Redis pool  get resource spend " + (endTime1 - startTime) + " ms.");
			if (writeRedisClient != null) {
				try {
					writeRedisClient.set(key.getBytes("UTF-8"), value.getBytes("UTF-8"));
				} catch (Exception re) {
					logger.error("set begin error ! key {}", key);
					logger.error("set begin error !", re);
				}
				long endTime2 = System.currentTimeMillis();
				logger.trace("2 Redis pool  set spend " + (endTime2 - endTime1) + " ms.");
				tag = true;
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
				long endTime3 = System.currentTimeMillis();
				logger.trace("3 Redis pool  return resource spend " + (endTime3 - endTime2) + " ms.");
			} else {
				System.out.println("writeRedisClient is null");
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} setString   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
	}

	/**
	 * 根据key获取int
	 * 
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key) {
		Integer obj = null;
		if (!StringUtils.isEmpty(key)) {
			logger.trace("key:{} getString  begin !", key);
			Jedis readRedisClient = null;
			try {
				readRedisClient = (Jedis) readRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (readRedisClient != null) {
				try {
					byte[] bs = readRedisClient.get(key.getBytes("UTF-8"));
					if (null != bs && bs.length > 0) {
						obj = Integer.valueOf(new String(bs, "UTF-8"));
					}
				} catch (Exception re) {
					logger.error("set begin error ! key {}", key);
					logger.error("set begin error !", re);
				}
				try {
					readRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} getString   end!", key);
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
		return obj;
	}

	/**
	 * 根据key存放int类型value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setInteger(String key, Integer value) {
		if (!StringUtils.isEmpty(key) && null != value) {
			logger.trace("key:{} setString  begin !", key);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					writeRedisClient.set(key.getBytes("UTF-8"), value.toString().getBytes("UTF-8"));
				} catch (Exception re) {
					logger.error("set begin error ! key {}", key);
					logger.error("set begin error !", re);
				}
				tag = true;
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} setString   end!", key);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
	}

	/**
	 * 从队列的左边入队一个或多个元素
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public boolean lpush(String key, String... values) {
		if (null != values && values.length > 0) {
			logger.trace("values:{} lpush  begin !", values);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					writeRedisClient.lpush(key, values);
				} catch (Exception re) {
					logger.error("lpush begin error ! keys {}", values);
					logger.error("lpush begin error !", re);
				}
				tag = true;
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("values:{} lpush   end!", values);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
	}

	/**
	 * 从队列的左边入队一个或多个元素
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public boolean rpush(String key, String... values) {
		if (null != values && values.length > 0) {
			logger.trace("values:{} rpush  begin !", values);
			Jedis writeRedisClient = null;
			boolean tag = false;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					writeRedisClient.rpush(key, values);
				} catch (Exception re) {
					logger.error("lpush begin error ! keys {}", values);
					logger.error("lpush begin error !", re);
				}
				tag = true;
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("values:{} rpush   end!", values);
			return tag;
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
	}

	/**
	 * 从队列的左边出队一个元素
	 * 
	 * @param key
	 * @return
	 */
	public String lpop(String key) {
		String res = null;
		if (null != key && key.length() > 0) {
			logger.trace("key:{} lpop  begin !", key);
			Jedis writeRedisClient = null;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					res = writeRedisClient.lpop(key);
				} catch (Exception re) {
					logger.error("lpop begin error ! key {}", key);
					logger.error("lpop begin error !", re);
				}
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("values:{} lpop   end!", key);
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
		return res;
	}

	/**
	 * 从队列的右边出队一个元素
	 * 
	 * @param key
	 * @return
	 */
	public String rpop(String key) {
		String res = null;
		if (null != key && key.length() > 0) {
			logger.trace("key:{} rpop  begin !", key);
			Jedis writeRedisClient = null;
			try {
				writeRedisClient = (Jedis) writeRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (writeRedisClient != null) {
				try {
					res = writeRedisClient.rpop(key);
				} catch (Exception re) {
					logger.error("rpop begin error ! key {}", key);
					logger.error("rpop begin error !", re);
				}
				try {
					writeRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("values:{} rpop   end!", key);
		} else {
			throw new IllegalArgumentException("param key is null or  list is empty");
		}
		return res;
	}

	/**
	 * 根据一组key获取一组对象
	 * 
	 * @param strings
	 * @return
	 */
	public List<Serializable> mget(List<String> keys) {
		List<Serializable> list = null;
		if (null != keys && keys.size() > 0) {
			logger.trace("key:{} mget  begin !", keys);
			Jedis readRedisClient = null;
			try {
				readRedisClient = (Jedis) readRedisPool.getResource();
			} catch (JedisConnectionException e) {
				logger.error("redis connection pool havn't idle contention !", e);
			}
			if (readRedisClient != null) {
				try {
					String[] ks = new String[keys.size()];
					keys.toArray(ks);
					readRedisClient.mget(ks);
					byte[][] byteks = new byte[keys.size()][];
					for (int i = 0; i < keys.size(); i++) {
						byteks[i] = keys.get(i).getBytes("UTF-8");
					}
					List<byte[]> byteArrayList = readRedisClient.mget(byteks);
					if (null != byteArrayList && byteArrayList.size() > 0) {
						list = new ArrayList<Serializable>();
						for (byte[] barray : byteArrayList) {
							list.add(getObjectFromBytes(barray));
						}
					}
				} catch (Exception re) {
					logger.error("mget begin error ! keys {}", keys);
					logger.error("mget begin error !", re);
				}
				try {
					readRedisClient.close();
				} catch (RuntimeException e) {
					logger.error("redis connection pool couldn't return contention !", e);
				}
			} else {
				logger.error("redis connection pool havn't idle contention !");
			}
			logger.trace("key:{} mget   end!", keys);
		} else {
			throw new IllegalArgumentException("param keys is empty");
		}
		return list;
	}
	
	public JedisPool getMqRedisPool(){
		return this.mqRedisPool;
	}

}
