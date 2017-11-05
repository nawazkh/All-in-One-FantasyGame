<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>League Dashboard</title>
<!--  ${pageContext.request.contextPath} - gives you path of the project -->
<!-- jQuery -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/css/jQuery.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/LeagueInfoPage.css" media="screen" />
<style type="text/css">
<!--
body { 
 padding:0;
 margin:0;
}
-->
</style>
</head>
<body onload="javascript:checkJoinButton('${leagueId}','${userId}')">
	<div class="colorstrip1">
	</div>
	<div id="mainDiv" class="maindiv">
		<div class="colorstrip2">
			<img src="${pageContext.request.contextPath}/resources/UIAssets/league1pic.png" class="userimage">
			<div class="welcomemsg">
			<b></b>
			<b class="name">${name}</b>
			</div>
			<img src="${pageContext.request.contextPath}/resources/UIAssets/bannerdesign.png" class="bannerdesign">
			<button id="joinLeague" onclick="javascript:createTeam('${leagueId}','${userId}')"  class="joinleague">join league</button>
 		</div>
 		<div class="leagueinfo">
 			<div class="players">
 				<div class="colorstrip22">
 					<b>players</b>
 					<div class="playerlist">
 						<a>user 1</a>
 						<a>user 2</a>
 						<a>user 3</a>
 						<a>user 4</a>
 						<a>user 5</a>
 						<a>user 6</a>
 						<a>user 7</a>
 						<a>user 8</a>
 						<a>user 9</a>
 						<a>user 10</a>
 						<a>user 11</a>
 						<a>user 12</a>
 						<a>user 13</a>
 						<a>user 14</a>
 					</div>
 				</div>
 			</div> 
 			<div class="previousgames">
 				<div class="colorstrip23">
 					<b>previous matches</b>
 					<div class="previousgameslist">
 						<a>user 1</a>
 						<a>user 2</a>
 						<a>user 3</a>
 					</div>
 				</div>
 			</div> 
 			<div class="upcominggames">
 				<div class="colorstrip23">
 					<b>upcoming matches</b>
 					<div class="upcominggameslist">
 						<a>team 1 v team 2</a>
 						<a>team 2 v team 3</a>
 						<a>team 3 v team 1</a>
 						<a>team 1 v team 2</a>
 						<a>team 2 v team 3</a>
 						<a>team 3 v team 1</a>
 						<a>team 1 v team 2</a>
 						<a>team 2 v team 3</a>
 						<a>team 3 v team 1</a>
 						<a>team 1 v team 2</a>
 						<a>team 2 v team 3</a>
 						<a>team 3 v team 1</a>
 					</div>
 				</div>
 			</div> 			
	 	</div>
 		
 		<div class="colorstrip10"></div>
		<div class="colorstrip11"></div>
		<div class="colorstrip12"></div>
		<div class="colorstrip13">
			<b class="companybanner">a group 4 project ©</b>
		</div>
	</div>

	<script>
	
function checkJoinButton(leagueId,userId){
	$.ajax({
	    url : '/All-In-One-FantasyGame/checkLeagueMembership',
	    type: 'post',
	    data : {userId:userId,leagueId:leagueId},
	    
	    success: function(data)
	   
	    {	
	    	
	    	if(data == "true"){
	    	$('#joinLeague').hide();
	    	}
	    	
	    },
	    error: function (jqXHR, textStatus, errorThrown)
	    {
	    	alert("something went wrong.Contact Admin");
	    }
	});
}


function createTeam(leagueId,userId){
	alert(leagueId+"Came to league info page"+userId);
	var f = document.createElement("form");
	f.setAttribute('method',"post");
	f.setAttribute('action',"/All-In-One-FantasyGame/redirectToTeam");

	var i = document.createElement("input"); //input element, text
	i.setAttribute('type',"hidden");
	i.setAttribute('name',"leagueId");
	i.setAttribute('value',leagueId);
	f.appendChild(i);
	var j = document.createElement("input");
	j.setAttribute('type',"hidden");
	j.setAttribute('name',"userId");
	j.setAttribute('value',userId);
	f.appendChild(j);
	document.body.appendChild(f);
	console.log(f);
	f.submit();
	}
	
	</script>

</body>
</html>