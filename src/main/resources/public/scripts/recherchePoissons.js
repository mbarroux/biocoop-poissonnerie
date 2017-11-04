var delay = (function(){
    var timer = 0;
    return function(callback, ms){
        clearTimeout (timer);
        timer = setTimeout(callback, ms);
    };
})();

$("#searchPoisson").keyup(function(){
    delay(function(){
        var q = $("#searchPoisson").val();
        $.getJSON(
            "/poisson/findByNom?nom=" + q,
            function(data) {
            var $results = $("#search-results");
                $results.empty();
                $results.append("<ul>");

                $.each(data, function(i, poisson){
                    $results.append("<li><a href='/poisson/" + poisson.code + "'>" + poisson.espece + "</a></li>");
                });
                $results.append("</ul>");
            }
        );
    }, 300 );
});