$(document).ready(function () {

    const dom = {

        init: function () {
            console.log("loaded");
            eventApplier.addEventToFilters();
            $("#passwordField, #confirmPasswordField").on("input", events.checkPasswordsMatch);
            $("#userNameRegistrationField").on("input", events.checkUserNameExists);
            $("#emailField").on("input", events.checkEmailExists);
            $("#loginButton").click(events.authenticateLogIn);
        }

    };


    const eventApplier = {

        addEventToFilters: function () {
            $(".topic").click(events.filter);
        }

    };


    const events = {

        filter: function (event) {
            let id = $(event.target).attr("id");
            ajax.refreshIndex(id);
        },

        buildFilteredPage: function (response) {
            $(".funfact-data").empty();
            $.each(response, function (i, funfact) {
                $(".funfact-data").append(
                                        `<h3>${funfact.title}</h3>
                                         <h5>${funfact.author}</h5>
                                         <p>${funfact.description}</p>`)
            })
        },

        checkPasswordsMatch: function () {
            let password = $("#passwordField").val();
            let confirmPassword = $("#confirmPasswordField").val();

            if (password != confirmPassword) {
                $("#passwordLabel").html("Passwords do not match!").css("color", "red");
                $("#register-button").prop("disabled", true);
            }else {
                $("#passwordLabel").html("Passwords match.").css("color", "green");
                $("#register-button").prop("disabled", false);
            }
        },

        checkUserNameExists: function () {
            let userName = $("#userNameRegistrationField").val();
            if(userName.length>=5) {
                ajax.checkUserName(userName);
            }
        },

        userNameStatus: function (response) {
            if (response) {
                $("#userNameRegLabel").html("User already exists!").css("color", "red");
                $("#register-button").prop("disabled", true);
            }else {
                $("#userNameRegLabel").html("Username OK!").css("color", "green");
                $("#register-button").prop("disabled", false);
            }
        },

        checkEmailExists: function () {
            let email = $("#emailField").val();
            if(email.length >=5 && email.includes("@") && email.includes(".")) {
                ajax.checkEmail(email);
            } else {
                $("#emailLabel").empty();
                $("#register-button").prop("disabled", true);
            }
        },

        emailStatus: function (response) {
            if (response) {
                $("#emailLabel").html("E-mail address already in database!").css("color", "red");
                $("#register-button").prop("disabled", true);
            }else {
                $("#emailLabel").html("E-mail-address OK!").css("color", "green");
                $("#register-button").prop("disabled", false);
            }
        },

        authenticateLogIn: function (event) {
            event.preventDefault();
            ajax.authenticateLogIn();
        }
    };


    const ajax = {

        refreshIndex: function (id) {
            $.ajax({
                type: "POST",
                url: "/api/filter",
                dataType: "json",
                data: {"id":id},
                contentType: "application/json",
                success: function (response) {
                    events.buildFilteredPage(response);
                },
                error: function (response) {
                    console.log("refreshIndex error:" + response.responseText);
                }
            });
        },

        checkUserName: function (userName) {
                $.ajax({
                    type: "POST",
                    url: "/api/check-username",
                    dataType: "json",
                    data: {"userName":userName},
                    contentType: "application/json",
                    success: function (response) {
                        events.userNameStatus(response);
                    },
                    error: function (response) {
                        console.log("checkUserName error:" + response.responseText);
                    }
                });
            },

        checkEmail: function (email) {
            $.ajax({
                type: "POST",
                url: "/api/check-email",
                dataType: "json",
                data: {"email":email},
                contentType: "application/json",
                success: function (response) {
                    events.emailStatus(response);
                },
                error: function (response) {
                    console.log("checkEmail error:" + response.responseText);
                }
            });
        },

        authenticateLogIn: function () {
            $.ajax({
                type: "POST",
                url: "/api/authenticate",
                data: $("#loginForm").serialize(),
                success: function () {
                    location.reload();
                },
                error: function () {
                    $("#loginLabel").html("User name or password incorrect!").css("color", "red");
                }
            });
        }

    };

    dom.init();

});
