declare var __viewContext: nts.uk.ViewContext;

declare module nts.uk {
    interface ViewContext {
        primitiveValueConstraints: { [key: string]: nts.uk.ui.validation.PrimitiveValueConstraint };
    }

    
}