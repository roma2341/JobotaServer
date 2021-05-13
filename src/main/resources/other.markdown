``var userDTO = Mappers.getMapper(PlatformUserMapper::class.java).convertToDto(platformUser);
Mockito.`when`(platformUserService!!.create(platformUser)).thenReturn(Mono.just(platformUser));
.expectBody(PlatformUser::class.java
.consumeWith{result -> Assertions.assertEquals(platformUser,result.responseBody)}
Mockito.verify(platformUserService, times(1))!!.create(platformUser)``