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
<body>
	<div class="colorstrip1">
			<!--<tr>
				<b>aaaaa</b>
				<b>bbbbb</b>
			</tr>-->
	</div>
	<div id="mainDiv" class="maindiv">
		<div class="colorstrip2">
			<img src="${pageContext.request.contextPath}/resources/UIAssets/user1.jpeg" class="userimage">
			<div class="welcomemsg">
			<b class="name">${name}</b>
			</div>
			<img src="${pageContext.request.contextPath}/resources/UIAssets/bannerdesign.png" class="bannerdesign">
 		</div>
 		<div class="leagueinfo">
 			
	 	</div>
 		
 		<div class="colorstrip10"></div>
		<div class="colorstrip11"></div>
		<div class="colorstrip12"></div>
		<div class="colorstrip13">
			<b class="companybanner">a group 4 project ©</b>
		</div>
	</div>

	<script>
	var mainDiv = document.getElementById("mainDiv");
	var profileButton = document.getElementById("editProfile");
	var profilePageModal = document.getElementById("profilePageModal");
	var closeProfileButton = document.getElementById("closeProfile");
	
	window.onclick = function(event) {
		if(event.target.parentNode.className != "" && event.target.parentNode.className != "signup-modal-content" && event.target.parentNode.className != "signupmodalcss" && event.target.parentNode.className != "modal-content" && event.target.parentNode.className != "loginmodalcss" && event.target.parentNode.className != "forgotuorpdiv"){
			if(event.target.parentNode.className !="loginbuttondiv"){
				if(event.target.parentNode.className != "signupbuttondiv"){
				    if (event.target.parentNode.className != loginModal) {
				        loginModal.style.display = "none";
				        mainDiv.classList.remove("blur");
				    }
				    if (event.target.parentNode.className != signupModal){
				    	signupModal.style.display = "none";
				        mainDiv.classList.remove("blur");
				    }
				}
			}
		}		
	}
	
	profileButton.onclick = function() {
	    profilePageModal.style.display = "block";
	    mainDiv.classList.add("blur");
	}
	
	closeProfileButton.onclick = function() {
		profilePageModal.style.display = "none";
	    mainDiv.classList.remove("blur");
	}

	
	var updateProfileDiv = document.getElementById("updateProfileImage");
	var fileUploadDiv = document.getElementById("imageUpload");
	

	updateProfileDiv.onclick = function() {
		 readURL(fileUploadDiv);
	}
	
	
	    var readURL = function(input) {
	        if (input.files && input.files[0]) {
	            var reader = new FileReader();

	            reader.onload = function (e) {
	                $('.uploadprofileimage').attr('src', e.target.result);
	            }
	    
	            reader.readAsDataURL(input.files[0]);
	        }
	    }
	    

	    $(".file-upload").on('change', function(){
	        readURL(this);
	    });
	    
	    $(".updateprofileimage").on('click', function() {
	       $(".fileUpload").click();
	       readURL(this);
	    });


	function fetchLeagues() {
		jQuery.ajax({
			
		    url : "All-In-OneFantasyGame/fetchLeagues",
		    type: "POST",
		    data : {},
		    success: function(data, textStatus, jqXHR)
		    {
		        //data - response from server
		        jQuery("#selectedRestaurants").html(data);
		    },
		    error: function (jqXHR, textStatus, errorThrown)
		    {
		 		console.log(errorThrown);
		    }
		});
		}
	
	</script>
	
<script>
function sendFetchReq(sportName,iD){
	$.ajax({
	    url : '/All-In-One-FantasyGame/fetchLeagues',
	    type: 'post',
	    data : {SPORT_NAME:sportName},
	    dataType : 'json',
	    success: function(data)
	    {	$('#'+iD).empty();
	    	$(data.League).each(function(index,value){$('#'+iD).append('<a href=https://www.google.com >'+value.League+'</a>');})
	    	$('#'+iD).append('<a href="#" style="background-color: #ffbf03">create a new private league ></a>');
	    },
	    error: function (jqXHR, textStatus, errorThrown)
	    {
	    	$('#'+iD).empty();
	 		$('#'+iD).append('<a href="#" >No Leagues></a>');
			$('#'+iD).append('<a href="#" style="background-color: #ffbf03">create a new private league ></a>');
	    }
	});
}
</script>

</body>
</html>