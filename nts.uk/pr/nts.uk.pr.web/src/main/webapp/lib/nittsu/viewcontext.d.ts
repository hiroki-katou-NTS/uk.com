declare var __viewContext: ViewContext;

interface ViewContext {
    rootPath: string;
    primitiveValueConstraints: { [key: string]: any };
}