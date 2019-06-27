<template>
    <div class="documentscontrolsinput-time-range">
        <h5>1. {{'sample' | i18n}}</h5>
        <button class="btn btn-secondary" v-on:click="validate">Validate All</button>

        <nts-time-range-input 
            v-model="timeRangeValue1"
        />

        <hr/>

        <nts-time-range-input v-model="timeRangeValue2" v-bind:showTile="true" name="Time Range"/>

        <markdown/>

    </div>
</template>