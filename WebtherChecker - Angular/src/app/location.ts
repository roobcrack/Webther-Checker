export interface Main {
    temp: number; 
    humidity: number;
}

export interface Sys {
    country: string;
}

export interface Location {
    name: string; 
    main: Main; 
    sys: Sys; 
}
