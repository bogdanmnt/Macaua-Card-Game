
window.setInterval(function(){
 checkGame();
    console.log("VERIFIC");
}, 1000);
var idJucator = 0;
var responseMare="";
$("#login").on("click", function () {
    idJucator = $("#id-case").val();
    $.post("http://192.168.100.6:8080/join-game", {
        "id-jucator": idJucator
        , "username": $("#username").val()
    }, function (response) {
        alert("Bine ai venit " + $("#username").val() + " !");
        $(".nume-jucator").html($("#username").val());
       
    });
});
var afisat = 0;
function checkGame() {
    $.post("http://192.168.100.6:8080/info-joc", {}, function (response) {
        responseMare=response;
        
        
        if (response.inceput == true) {
            if(response.jucatorCurent.id==idJucator && afisat==0){
                alert("E RANDUL TAU!");
                afisat=1;
            }
            
            $(".info-joc").css("display", "block");
            $(".carteDeJos").css("background-image", "url(cards/" + response.carteaDeJos.symbol + "_of_" + response.carteaDeJos.color + ".png)");
            var jucatori = "";
            for (var i = 0; i < response.jucatori.length; i++) {
                jucatori = jucatori + "<div class='jucator'>" + response.jucatori[i].username + "</div>";
            }
            $(".jucatori").html(jucatori);
            var carti = "";
           
            for (var i = 0; i < response.jucatori.length; i++) {
               
                if (response.jucatori[i].id == idJucator) {
                   
                    for (var j = 0; j < response.jucatori[i].cards.length; j++) {
                      
                        carti = carti + "<div class='carte' id="+response.jucatori[i].cards[j].symbol+"_" + response.jucatori[i].cards[j].color+" style='background-image:url(cards/" + response.jucatori[i].cards[j].symbol + "_of_" + response.jucatori[i].cards[j].color + ".png)'></div>";
                    }
                }
            }
            $(".carti").html(carti);
          $(".carte").on("click",function(){
              if(response.jucatorCurent.id==idJucator){
                  $.post("http://192.168.100.6:8080/pune-carte", {
        "id-jucator": idJucator
        , "carte": $(this).attr('id')
    }, function (response) {
       if(response=="ok"){
           alert("Carte pusa");
        afisat=0;   
       }
       if(response=="nok")
           alert("Mutare ilegala");
        if(response=="win")
            alert("FELICITARI!!! AI CASTIGAT");
       
    });
                  
              }
              else{
                  alert("NU E RANDUL TAU");
                  
              }
              
              
              
          });
        }
    });
}
$(".startJoc").on("click", function () {
    $.post("http://192.168.100.6:8080/start-joc", {}, function (response) {
        alert("Joc inceput");
    });
});

$(".trageTarte").on("click",function(){
    
    if(responseMare.jucatorCurent.id==idJucator){
         $.post("http://192.168.100.6:8080/trage-carte", {}, function (response) {
        
    });
        
        
    }
    else{
        alert("NU E RANDUL TAU");
        
    }
    
    
    
});
