$(document).ready(function(){
	  
	  $("#button1").on('click',function(){
		$.ajax({
		type:'GET',
		url:'/function',
		data:{
			fromCurr:$("#fromCurr").val(),
			amt1:$("#amount1").val(),
			targetCurr:$("#targetCurr").val()
		},
		success: function(data){
			$("#amount2").val(data);
		}
	});
	});

});
