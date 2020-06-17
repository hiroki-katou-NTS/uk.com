import { _ } from '@app/provider';
export interface Item {
     isVisible: Boolean;
     title: String;
     lstItem: SubItem[];
}
export interface SubItem {
     isVisible: Boolean;
     content: String;
}
export interface InputPram {
     name: String;
}