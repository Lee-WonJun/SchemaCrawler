package schemacrawler.tools.commandline;

import picocli.CommandLine.Command;
import schemacrawler.tools.commandline.command.CommandLineHelpCommand;
import schemacrawler.tools.commandline.command.ConfigFileCommand;
import schemacrawler.tools.commandline.command.ConnectCommand;
import schemacrawler.tools.commandline.command.ExecuteCommand;
import schemacrawler.tools.commandline.command.FilterCommand;
import schemacrawler.tools.commandline.command.GrepCommand;
import schemacrawler.tools.commandline.command.LimitCommand;
import schemacrawler.tools.commandline.command.LoadCommand;
import schemacrawler.tools.commandline.command.LogCommand;
import schemacrawler.tools.commandline.shell.AvailableCommandsCommand;
import schemacrawler.tools.commandline.shell.AvailableServersCommand;
import schemacrawler.tools.commandline.shell.DisconnectCommand;
import schemacrawler.tools.commandline.shell.ExitCommand;
import schemacrawler.tools.commandline.shell.SweepCommand;
import schemacrawler.tools.commandline.shell.SystemCommand;

@Command(
    subcommands = {
      CommandLineHelpCommand.class,
      LogCommand.class,
      ConfigFileCommand.class,
      ConnectCommand.class,
      FilterCommand.class,
      GrepCommand.class,
      LimitCommand.class,
      LoadCommand.class,
      ExecuteCommand.class,
      AvailableCommandsCommand.class,
      AvailableServersCommand.class,
      DisconnectCommand.class,
      SweepCommand.class,
      SystemCommand.class,
      ExitCommand.class
    })
public class SchemaCrawlerShellCommands {}
