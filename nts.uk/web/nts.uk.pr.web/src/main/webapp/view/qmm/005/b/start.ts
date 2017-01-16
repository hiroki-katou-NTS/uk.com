let __viewContext: any;

__viewContext.primitiveValueConstraints.LayoutCode = {
    valueType: 'String',
    maxLength: 6
};

__viewContext.ready(function() {
    let screenModel = new nts.uk.pr.view.qmm005.b.viewmodel.ViewModel();
    this.bind(screenModel);
});