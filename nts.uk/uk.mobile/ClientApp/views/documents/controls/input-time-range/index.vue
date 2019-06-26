<template>
    <div class="documentscontrolsinput-time-range">
        <h5>1. {{'sample' | i18n}}</h5>
        <button class="btn btn-secondary" v-on:click="validate">Validate All</button>
        <nts-time-range-input 
            v-bind:showTile="true"
            name="Time Range"
            v-model="timeRangeValue"
        />

    </div>
</template>