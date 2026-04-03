FROM adoptopenjdk/openjdk11

# Prepare non-root user
ARG USERNAME=aisco
ARG USER_UID=1000
ARG USER_GID=${USER_UID}
RUN groupadd --gid ${USER_GID} ${USERNAME} \
    && useradd -s /bin/bash --uid ${USER_UID} --gid ${USER_GID} -m ${USERNAME}

# Copy codebase into the container image
WORKDIR /home/${USERNAME}/workspace
COPY --chown=${USERNAME}:${USERNAME} . .
RUN chown -R ${USERNAME}:${USERNAME} /home/${USERNAME}

# Run interactive Bash shell when running the container
USER ${USERNAME}
CMD [ "/bin/bash" ]