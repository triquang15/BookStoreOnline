$(document).ready(function() {

		$("#customerForm").validate({
			rules : {
				email : {
					required : true,
					email : true
				},
				firstname : "required",
				lastname : "required",
				password : "required",

				confirmPassword : {
					required : true,
					equalTo : "#password"
				},

				phone : "required",
				address1 : "required",
				address2 : "required",
				city : "required",
				state : "required",
				zipCode : "required",
				country : "required",
			},

			messages : {
				email : {
					required : "Please enter email address",
					email : "Please enter a valid email address"
				},
				firstname : "Please enter first name",
				lastname : "Please enter first name",
				password : "Please enter password",

				confirmPassword : {
					required : "Please enter confirm password",
					equalTo : "Password does not match"
				},

				phone : "Please enter phone number",
				address1 : "Please enter address 1",
				address2 : "Please enter address 2",
				city : "Please enter city",
				state : "Please enter state",
				zipCode : "Please enter zipcode",
				country : "Please enter country",
			}
		});

		$("#buttonCancel").click(function() {
			history.go(-1);
		});
	});