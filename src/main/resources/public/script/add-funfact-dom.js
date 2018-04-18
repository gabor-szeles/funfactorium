$(document).ready(function () {

    const dom = {

        init: function () {
            console.log("loaded")
            $("#addFactTopics").multiselect({
                dropRight: true,
                enableFiltering: true
            });
        }

    };

    dom.init();
});