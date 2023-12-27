package com.example.jdk21.config;

import cn.hutool.core.util.IdUtil;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * @author admin
 * @date 2023/12/27 16:24
 */
public class SnowflakeIDGenerator implements IdentifierGenerator {

    @Override
    public String generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return String.valueOf(IdUtil.getSnowflake().nextId());
    }
}
