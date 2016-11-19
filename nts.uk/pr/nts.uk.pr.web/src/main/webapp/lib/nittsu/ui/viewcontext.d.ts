declare var __viewContext: ViewContext;

interface ViewContext {
    rootPath: string;
    primitiveValueConstraints: { [key: string]: any };
    
    ready: (callback: () => void) => void;
    bind: (viewModel: any) => void;
}