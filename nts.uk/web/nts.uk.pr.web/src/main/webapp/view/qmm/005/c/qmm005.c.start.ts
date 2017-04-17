__viewContext['primitiveValueConstraints']['ProcessingName'] = {
    valueType: 'String',
};

module qmm005.c {
    __viewContext.ready(() => {
        __viewContext.bind(new ViewModel());
    });
}