module nts.uk.at.view.kdw004.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModelKDW004A();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            let img = document.createElement("span");
            img.className = "windows-img";
            let colorBtn = document.getElementById("colorBtn");
            colorBtn.innerHTML = "";
            colorBtn.appendChild(img);
            let txt = document.createElement("span");
            txt.innerHTML = nts.uk.resource.getText("KDW004_7");
            colorBtn.appendChild(txt);
        }).fail((messageId) => {
            nts.uk.ui.dialog.alert({ messageId: messageId }).then(() => {
                nts.uk.request.jump("com", "view/ccg/008/a/index.xhtml");  
            });
        });
    });
}