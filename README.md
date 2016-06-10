# NoCmds
**Download:** [releases](https://github.com/piratjsk/NoCmds/releases) 

### Permissions
`nocmds.bypass` - Allows to use blocked commands.  
`nocmds.admin` - Gives access to 'nocmds reload' and 'nocmds list' commands.

### Commands
`/nocmds` - Prints useless line of text.  
`/nocmds reload` - Reloads plugin configuration.  
`/nocmds list` - Prints blocked commands.

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
