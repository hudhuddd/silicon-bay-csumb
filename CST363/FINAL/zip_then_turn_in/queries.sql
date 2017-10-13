## Query displays all server information and backups, even if there have been no backups.
SELECT server.serverID, client.clientID, server.serverName, client.clientName, role.role, backup.backupID, backup.backupSource, backup.backupDestination, backup.frequency, backup.notes
FROM server
	JOIN client
		ON server.clientID = client.clientID
	JOIN serverrole
		ON server.serverID = serverrole.serverID
	JOIN role
		ON serverrole.roleID = role.roleID
	LEFT JOIN backup
		ON server.serverID = backup.serverID
ORDER BY backup.backupID;

## Find clients whose servers have no backups
SELECT client.clientName, COUNT(backup.backupID)
FROM server
	JOIN client
		ON server.clientID = client.clientID
	LEFT JOIN backup
	ON server.serverID = backup.serverID
GROUP BY server.serverID
HAVING COUNT(backup.backupID) = 0;

## Count how many backups each server has
SELECT server.serverName, COUNT(backup.backupID)
FROM server
	LEFT JOIN backup
	ON server.serverID = backup.serverID
GROUP BY server.serverID;

## Find server with the most amount of roles
SELECT *
FROM(
	SELECT server.serverID, server.serverName, COUNT(server.serverID) AS count_roles
	FROM server
		JOIN serverrole
			ON server.serverID = serverrole.serverID
		JOIN role
			ON serverrole.roleID = role.roleID
	GROUP BY server.serverID
	) AS countroles
ORDER BY countroles.count_roles DESC
LIMIT 1;

## Find all servers that have an Active Directory role
SELECT *
FROM server
	JOIN client
		ON server.clientID = client.clientID
	JOIN serverrole
		ON server.serverID = serverrole.serverID
	JOIN role
		ON serverrole.roleID = role.roleID
WHERE role.role = 'Active Directory';

CREATE OR REPLACE VIEW `latest_backup` AS
SELECT s.serverName, client.clientName, backup.backupID, backup.backupSource, backup.backupDestination
FROM server AS s
	JOIN client
		ON s.clientID = client.clientID
	JOIN backup
		ON s.serverID = backup.serverID
WHERE backupID = 
	(SELECT MAX(backup.backupID)
	FROM backup
    WHERE backup.serverID = s.serverID
    );