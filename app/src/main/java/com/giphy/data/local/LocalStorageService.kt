package com.giphy.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalStorageService(
    private val dataStore: DataStore<Preferences>
) {

    private object PreferencesKeys {
        val DELETED_GIPHY_IDS_SET = stringSetPreferencesKey("deleted_giphy_ids_set")
    }

    suspend fun setDeletedGiphyIdsSet(set: Set<String>) {
        dataStore.edit {
            it[PreferencesKeys.DELETED_GIPHY_IDS_SET] = set
        }
    }

    suspend fun getDeletedGiphyIdsSet(): Set<String> {
        return dataStore.data
            .map { it[PreferencesKeys.DELETED_GIPHY_IDS_SET] ?: setOf() }
            .first()
    }
}
