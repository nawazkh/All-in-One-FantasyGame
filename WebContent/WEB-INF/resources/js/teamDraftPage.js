
	
	var teamNameEditForm = document.getElementById("teamNameEdit");
	var playerRoster = document.getElementById("player-roster");
	var userTeam = document.getElementById("user-team");


	var saveButton = document.getElementById("saveButton");
	var playerData="";
   	
	function updatedTeamName(){
		var updatedName=document.getElementById("updateTeamName").value;
		//alert("Function called");
		alert(updatedName);
	}
	
	function dragPlayer(player, event) {
	    event.dataTransfer.setData('Players', player.id);
	    
	}
	function dropPlayer1(target,event){
		var player = event.dataTransfer.getData('Players');
		target.appendChild(document.getElementById(player));
		countRoles();
	}
	function dropPlayer(target, event) {
	    var player = event.dataTransfer.getData('Players');
	   	target.appendChild(document.getElementById(player)); 
		var ind=0;	
		var listOfPlayerIds=[];
		var listItems = $("#user-team").find("p");
		console.log(listItems);
		listItems.splice(0,1);
		for ( ind = 0; ind < listItems.length; ind++ ) {
		   // console.log($(listItems[ind]).attr('id'));
		    listOfPlayerIds.push($(listItems[ind]).attr('id'));
		   
		}
		
		
		if(listOfPlayerIds.length > 11){
			alert("Please select 11 players only")
			
			document.getElementById("rosterList").append(document.getElementById(player));
			//target.removeChild(document.getElementById(player));
			
			
		}
		countRoles();
	}
		function countRoles(){
			var ind=0;	
			var rolesList=[];
			var roles = $("#user-team").find("p");
			roles.splice(0,1);
			for ( ind = 0; ind < roles.length; ind++ ) {
			  
			    rolesList.push($(roles[ind]).attr('role'));
			}
			
			var counts = {};
			for (var i = 0; i < rolesList.length; i++) {
    			counts[rolesList[i]] = 1 + (counts[rolesList[i]] || 0);
			}
			console.log(counts);
			return counts;
		}
	$( function() {
   	 $( "#sortable1, #sortable2" ).sortable({
   	   connectWith: ".connectedSortable"
   	 }).disableSelection();
  	} );
	function onLoadCalls(leagueId,userId,flag){
		populateTeam(userId,leagueId);
		
		fetchPlayerList(leagueId,'rosterList',userId);
		fetchRules(leagueId);
	}
	var rulesMap="";
	function fetchRules(leagueId){
		$.ajax({
			url : '/All-In-One-FantasyGame/rulesMap',
		    type: 'post',
		    data : {leagueId:leagueId},
		    dataType : 'json',
		    success: function(data){
		    	console.log(data);
		    	populateRules(data);
		    },
		    error: function (jqXHR, textStatus, errorThrown)
		    {
		    	populateRules("");
				console.log("Error while fetching rules");
		    }
		});
	}
	function populateRules(rules){
		rulesMap=rules;
		console.log(rulesMap);
	}

	function rulesCheck(counts,leagueId,userId,listOfPlayerIds,flag){
	
		var flag=Boolean(1==1)
		var missedRules=""
		$(rulesMap.rulesMap).each(function(index,value){
			console.log(value.role);
			if(counts.hasOwnProperty(value.role)){
				if(counts[value.role]>=parseInt(value.number)){
				console.log("Success");
				}else{
					missedRules+=" Please select atleast " + value.number +" " + value.role+"\n"
					flag=Boolean(1>1)
				}
			}
			else{
				missedRules+=" Please select atleast " + value.number +" " + value.role+"\n";
				
				flag=Boolean(1>1)
			}
			
		});
		if(flag){
		listOfPlayerIds=JSON.stringify(listOfPlayerIds);
		console.log("Thanks");
		callFormSubmit(leagueId,userId,listOfPlayerIds,flag);
		}
		else{
			alert(missedRules);
		}
	}
	function sendPlayerList(userId,leagueId,flag){
		//var teamName=document.getElementById("teamName").value;
		//var teamName=('#teamName').val();
		//alert(teamName);
		var ind=0;	
		var listOfPlayerIds=[];
		var rolesList=[];
		var listItems = $("#user-team").find("p");
		listItems.splice(0,1);
		for ( ind = 0; ind < listItems.length; ind++ ) {
		    console.log($(listItems[ind]).attr('id'));
		    listOfPlayerIds.push($(listItems[ind]).attr('id'));
		    rolesList.push($(listItems[ind]).attr('role'));
		}
		
		//console.log(rolesList.length);
		var counts = {};
		for (var i = 0; i < rolesList.length; i++) {
			counts[rolesList[i]] = 1 + (counts[rolesList[i]] || 0);
		}
		console.log(Object.keys(counts).length);
		var countOfRoles=Object.keys(counts).length;
		
		if(listOfPlayerIds.length < 11 ){
			alert("Please select atleast 11 players");
			return;
		}
		else if(listOfPlayerIds.length > 11){
			alert("Please select 11 players only");
			return;
		}
		else if(teamName==""){
			alert("Please Enter Team name.");
			return;
		}
		rulesCheck(counts,leagueId,userId,listOfPlayerIds,flag);
			
		
		
	}
	var playerType="";
	function playerTypeSearch(type){
		playerType=type;
		if(playerType.includes("ALL")){
			playerType="";
		}
		searchPlayerList();
	}
	

	function callFormSubmit(leagueId,userId,listOfPlayerIds,flag){
		$.ajax({
		    url : '/All-In-One-FantasyGame/createTeam',
		    type: 'post',
		    data : {userId:userId,leagueId:leagueId,listOfPlayerIds:listOfPlayerIds,flag:flag},
		    
		    success: function(data)
		   
		    {	
		    	if (data=="true")
		    	var f = document.createElement("form");
				f.setAttribute('method',"post");
				f.setAttribute('action',"/All-In-One-FantasyGame/redirectLeague");

				var i = document.createElement("input"); //input element, text
				i.setAttribute('type',"hidden");
				i.setAttribute('name',"redirectValue");
				i.setAttribute('value',leagueId);
				f.appendChild(i);
				var j = document.createElement("input");
				j.setAttribute('type',"hidden");
				j.setAttribute('name',"userId");
				j.setAttribute('value',userId);
				f.appendChild(j);
				var k = document.createElement("input");
				k.setAttribute('type',"hidden");
				k.setAttribute('name',"flag");
				k.setAttribute('value',flag);
				f.appendChild(k);
				document.body.appendChild(f);
				console.log(f);
				f.submit();
		    },
		    error: function (jqXHR, textStatus, errorThrown)
		    {
		    	alert("something went wrong.Contact Admin");
		    }
		});
	}
	
	