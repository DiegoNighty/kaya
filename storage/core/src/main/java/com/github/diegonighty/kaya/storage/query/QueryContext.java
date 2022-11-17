package com.github.diegonighty.kaya.storage.query;

import com.github.diegonighty.kaya.storage.query.token.QueryToken;

public record QueryContext(
        QueryToken method,
        QueryToken quantity,
        QueryToken filter
) {}
