// src/store.js
import { createStore } from 'vuex';

const store = createStore({
  state: {
    globalVariable: 'This is a global variable',
    cardAniPlayer: false,
  },
  mutations: {
    setGlobalVariable(state, newValue) {
      state.globalVariable = newValue;
    },
    setCardAniPlayer(state, newValue) {
      state.cardAniPlayer = newValue;
    }
  },
  actions: {
    updateGlobalVariable({ commit }, newValue) {
      commit('setGlobalVariable', newValue);
    },
    updateCardAniPlayer({ commit }, newValue) {
        commit('setCardAniPlayer', newValue);
        },
  },
  getters: {
    getGlobalVariable: (state) => state.globalVariable,
    getCardAniPlayer: (state) => state.cardAniPlayer
  },

});

export default store;
