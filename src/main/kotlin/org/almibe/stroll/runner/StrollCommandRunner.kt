/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/

package org.almibe.stroll.runner

import org.almibe.stroll.StrollResult
import org.almibe.stroll.loader.CommandArguments

class StrollCommandRunner {
    fun runCommandArguments(commandArguments: CommandArguments): StrollResult {
        TODO()
    }


//    private fun clearPropertiesAndLinks(entity: Entity) {
//        entity.propertyNames.forEach {
//            entity.deleteProperty(it)
//        }
//        entity.linkNames.forEach {
//            entity.deleteLinks(it)
//        }
//    }
//
//    private fun setPropertiesAndLinks(transaction: StoreTransaction, entity: Entity, commandArguments: CommandArguments) {
//        commandArguments.properties.forEach { property ->
//            when (property.value.tokenType) {
//                TokenType.DOUBLE -> entity.setProperty(property.key, property.value.tokenContent.toDouble())
//                TokenType.LONG -> entity.setProperty(property.key, property.value.tokenContent.trim('L').toLong())
//                TokenType.INT -> entity.setProperty(property.key, property.value.tokenContent.toInt())
//                TokenType.STRING -> entity.setProperty(property.key, property.value.tokenContent)
//                TokenType.KEYWORD -> entity.setProperty(property.key, property.value.tokenContent.toBoolean())
//                else -> {
//                    throw RuntimeException("Property type must be string, char, boolean, int, long or double.")
//                }
//            }
//        }
//        commandArguments.link.forEach {
//            val linkedEntity = transaction.getEntity(transaction.toEntityId(it.value.tokenContent))
//            entity.setLink(it.key, linkedEntity)
//        }
//        commandArguments.links.forEach {
//            val linkedEntity = transaction.getEntity(transaction.toEntityId(it.second.tokenContent))
//            entity.addLink(it.first, linkedEntity)
//        }
//    }
//
//    private fun runNew(commandString: String): JsonObject {
//        val itr: Iterator<StrollToken> = tokenize(commandString)
//        val new = itr.next()
//        assert(new.tokenType == TokenType.KEYWORD && new.tokenContent == "new")
//        val entityType: String = itr.next().tokenContent
//
//        val commandArguments = readCommandArguments(itr) ?: throw RuntimeException()
//
//        return entityStore.computeInTransaction { transaction ->
//            val entity = transaction.newEntity(entityType)
//            setPropertiesAndLinks(transaction, entity, commandArguments)
//            val result = JsonObject()
//            result.addProperty("operation", "new")
//            entityToJsonObject(entity)
//            result.add("result", entityToJsonObject(entity))
//            result
//        }
//    }
//
//    private fun runUpdate(commandString: String): JsonObject {
//        val itr: Iterator<StrollToken> = tokenize(commandString)
//        val update = itr.next()
//        assert(update.tokenType == TokenType.KEYWORD && update.tokenContent == "update")
//        val entityId: String = itr.next().tokenContent
//
//        val commandArguments = readCommandArguments(itr) ?: throw RuntimeException()
//
//        return entityStore.computeInTransaction { transaction ->
//            val entity = transaction.getEntity(transaction.toEntityId(entityId))
//            setPropertiesAndLinks(transaction, entity, commandArguments)
//            val result = JsonObject()
//            result.addProperty("operation", "update")
//            entityToJsonObject(entity)
//            result.add("result", entityToJsonObject(entity))
//            result
//        }
//    }
//
//    private fun runSet(commandString: String): JsonObject {
//        val itr: Iterator<StrollToken> = tokenize(commandString)
//        val set = itr.next()
//        assert(set.tokenType == TokenType.KEYWORD && set.tokenContent == "set")
//        val entityId: String = itr.next().tokenContent
//
//        val commandArguments = readCommandArguments(itr) ?: throw RuntimeException()
//
//        return entityStore.computeInTransaction { transaction ->
//            val entity = transaction.getEntity(transaction.toEntityId(entityId))
//            clearPropertiesAndLinks(entity)
//            setPropertiesAndLinks(transaction, entity, commandArguments)
//            val result = JsonObject()
//            result.addProperty("operation", "set")
//            entityToJsonObject(entity)
//            result.add("result", entityToJsonObject(entity))
//            result
//        }
//    }
//
//    private fun runDelete(commandString: String): JsonObject {
//        val itr: Iterator<StrollToken> = tokenize(commandString)
//        val delete = itr.next()
//        assert(delete.tokenType == TokenType.KEYWORD && delete.tokenContent == "delete")
//        val startBracketOrIdentity = itr.next()
//
//        return entityStore.computeInTransaction { transaction ->
//            var total = 0
//            when (startBracketOrIdentity.tokenType) {
//                TokenType.START_BRACKET -> {
//                    var next = itr.next()
//                    while (next.tokenType == TokenType.IDENTITY) {
//                        val entity = transaction.getEntity(transaction.toEntityId(next.tokenContent))
//                        entity.delete()
//                        total++
//
//                        next = itr.next()
//                        if (next.tokenType == TokenType.COMMA) {
//                            next = itr.next()
//                        }
//                    }
//                    assert(next.tokenType == TokenType.END_BRACKET)
//                }
//                TokenType.IDENTITY -> {
//                    val entity = transaction.getEntity(transaction.toEntityId(startBracketOrIdentity.tokenContent))
//                    entity.delete()
//                    total++
//                }
//                else -> throw RuntimeException("Unexpected argument passed to delete $startBracketOrIdentity")
//            }
//            val result = JsonObject()
//            result.addProperty("operation", "delete")
//            result.addProperty("total", total)
//            result
//        }
//    }
//
//    private fun runFind(commandString: String): JsonObject {
//        val itr: Iterator<StrollToken> = tokenize(commandString)
//        val find = itr.next()
//        assert(find.tokenType == TokenType.KEYWORD && find.tokenContent == "find")
//        val entityType: String = itr.next().tokenContent
//
//        val commandArguments = readCommandArguments(itr) ?: throw RuntimeException()
//
//        val resultLists: MutableList<List<Entity>> = mutableListOf()
//        return entityStore.computeInReadonlyTransaction { transaction ->
//            commandArguments.properties.forEach { property ->
//                val result = when(property.value.tokenType) {
//                    TokenType.INT ->
//                        transaction.find(entityType, property.key, property.value.tokenContent.toInt()).toList()
//                    TokenType.DOUBLE ->
//                        transaction.find(entityType, property.key, property.value.tokenContent.toDouble()).toList()
//                    TokenType.LONG ->
//                        transaction.find(entityType, property.key, property.value.tokenContent.toLong()).toList()
//                    TokenType.STRING ->
//                        transaction.find(entityType, property.key, property.value.tokenContent.toString()).toList()
//                    TokenType.KEYWORD ->
//                        transaction.find(entityType, property.key, property.value.tokenContent.toBoolean()).toList()
//                    else -> throw RuntimeException()
//                }
//                resultLists.add(result)
//            }
//            commandArguments.link.forEach { link ->
//                val links = transaction.findLinks(entityType,
//                        transaction.getEntity(transaction.toEntityId(link.value.tokenContent)),
//                        link.key)
//                resultLists.add(links.toList())
//            }
//            commandArguments.links.forEach { link ->
//                val links = transaction.findLinks(entityType,
//                        transaction.getEntity(transaction.toEntityId(link.second.tokenContent)),
//                        link.first)
//                resultLists.add(links.toList())
//            }
//            commandArguments.propertyExistsCheck.forEach { propertyName ->
//                val withProp = transaction.findWithProp(entityType, propertyName)
//                resultLists.add(withProp.toList())
//            }
//            commandArguments.linkExistsCheck.forEach { linkName ->
//                val withLinks = transaction.findWithLinks(entityType, linkName)
//                resultLists.add(withLinks.toList())
//            }
//            //TODO eventually handle ranges and startsWith here
//
//            val result = JsonObject()
//            result.addProperty("operation", "find")
//            val resultItr = resultLists.iterator()
//            val resultArray = JsonArray()
//            result.add("results", resultArray)
//
//            if (!resultItr.hasNext()) {
//                transaction.getAll(entityType).forEach {
//                    resultArray.add(entityToJsonObject(it))
//                }
//            } else {
//                var workingResults = resultItr.next().toMutableList()
//                while (resultItr.hasNext()) {
//                    workingResults = workingResults.intersect(resultItr.next()).toMutableList()
//                }
//                workingResults.forEach {
//                    resultArray.add(entityToJsonObject(it))
//                }
//            }
//            result
//        }
//    }
//
//    private fun entityToJsonObject(entity: Entity): JsonObject {
//        val properties = entity.propertyNames.map { propertyName ->
//            Pair(propertyName, entity.getProperty(propertyName).toString())
//        }
//        val links = entity.linkNames
//
//        val result = JsonObject()
//        val jsonArrayProperties = JsonArray()
//        val jsonArrayLinks = JsonArray()
//
//        properties.forEach {
//            val property = JsonObject()
//            property.addProperty(it.first, it.second)
//            jsonArrayProperties.add(property)
//        }
//
//        links.forEach {
//            jsonArrayLinks.add(it)
//        }
//
//        result.addProperty("entityType", entity.type)
//        result.addProperty("entityId", entity.toIdString())
//
//        result.add("properties", jsonArrayProperties)
//        result.add("links", jsonArrayLinks)
//
//        return result
//    }


}