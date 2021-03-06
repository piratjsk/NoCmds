# NoCmds
**Download:** [releases](https://github.com/piratjsk/NoCmds/releases) 

### Permissions
`nocmds.bypass` - Allows to use blocked commands.  
`nocmds.admin` - Gives access to 'nocmds' command.

### Commands
`/nocmds` - Prints list of available commands.  
`/nocmds reload` - Reloads plugin configuration.  
`/nocmds list` - Prints blocked commands.  
`/nocmds block <cmd> [cmd] [...]` - Block given command(s).  
`/nocmds unblock <cmd> [cmd] [...]` - Unblock given command(s).

### Usage
Just add commands you want to block to `config.yml` as next items of `blockedCommands` list.  
If command is added without slash at start then this command will be also blocked when used with any prefix.  
If command is added with slash at start then blocked is only this excact command.

##### Example:
```yaml
blockedCommands:
- ver
- /plugins
```
This configuration blocks following commands:  
`/ver`  
`/bukkit:ver`  
`/minecraft:ver`  
`/<any_prefix>:ver`  
and  
`/plugins`.
