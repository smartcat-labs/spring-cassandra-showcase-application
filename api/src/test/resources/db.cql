/*
  Create keyspace with Network Topology. This is more similar with keyspaces created on production
  servers. Also using lightweight transaction (ifNotExists) does not seem to work together with
  SimpleStrategy and consistency level EACH_QUORUM.
 */
CREATE KEYSPACE smartcatshowcase_test WITH replication = { 'class': 'NetworkTopologyStrategy', 'DC1': '1' };
USE smartcatshowcase_test;

/* Account */
CREATE TABLE account_by_email (
    email_address text,
    account_password text,
    first_name text,
    last_name text,
    registered_at timestamp,
    roles set<text>,
    profile_image_url text,
    PRIMARY KEY (email_address)
) WITH COMMENT='Accounts in system by email.';

CREATE TABLE account_by_external_source (
    external_source_id text,
    external_source text,
    email_address text,
    PRIMARY KEY ((external_source_id, external_source))
) WITH COMMENT='Account email by external source.';
