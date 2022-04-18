module nts.uk.at.view.knr001.b {

  __viewContext.ready(function() {
      var screenModel = new nts.uk.at.view.knr001.b.viewmodel.ScreenModel();
      screenModel.startPage().done(() => {
          __viewContext.bind(screenModel);
          $("#B1_2").focus();
      });
  });
}