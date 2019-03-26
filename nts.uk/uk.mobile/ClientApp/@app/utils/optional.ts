import { util } from "@app/utils/index"

export module optional {
    export function of<V>(value: V) {
        return new Optional(value);
    }
    
    export function empty() {
        return new Optional(null);
    }
    
    
    
    export class Optional<V> {
        value: V;
    
        constructor(value: V) {
            this.value = util.orDefault(value, null);
        }
    
        ifPresent(consumer: (value: V) => void) {
            if (this.isPresent()) {
                consumer(this.value);
            }
            return this;
        }
    
        ifEmpty(action: () => void) {
            if (!this.isPresent()) {
                action();
            }
            return this;
        }
    
        map<M>(mapper: (value: V) => M): Optional<M> {
            return this.isPresent() ? of(mapper(this.value)) : empty();
        }
    
        isPresent(): boolean {
            return this.value !== null;
        }
    
        get(): V {
            if (!this.isPresent()) {
                throw new Error('not present');
            }
            return this.value;
        }
    
        orElse(stead: V): V {
            return this.isPresent() ? this.value : stead;
        }
    
        orElseThrow(errorBuilder: () => Error) {
            if (!this.isPresent()) {
                throw errorBuilder();
            }
        }
    }
}