If ($CmdLine[2] == 'chrome')Then
   WinWaitActive( "Open" )
      Send(""&$CmdLine[1])
	  Send("{ENTER}")
	  EndIf
If ($CmdLine[2] == 'firefox')Then
   WinWaitActive( "File Upload" )
      Send(""&$CmdLine[1])
	  Send("{ENTER}")
	  EndIf